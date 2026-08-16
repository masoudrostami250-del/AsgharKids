package ir.asghar.kids

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var status: TextView
    private lateinit var tts: TextToSpeech
    private var recognizer: SpeechRecognizer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.rgb(255, 243, 224))
        }

        val title = TextView(this).apply {
            text = "🐶 اصغر و بچه‌هاش 🐶"
            textSize = 26f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(30, 70, 130))
            setPadding(10, 25, 10, 15)
        }

        val puppyView = PuppyView(this)

        status = TextView(this).apply {
            text = "اصغر، پامبول و مانگال منتظرن! 🐶❤️"
            textSize = 19f
            gravity = Gravity.CENTER
            setPadding(10, 15, 10, 15)
        }

        val talk = TextView(this).apply {
            text = "🎙️  با سه تا دوستت حرف بزن"
            textSize = 20f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(30, 120, 220))
            setPadding(20, 25, 20, 25)

            setOnClickListener {
                requestMicrophone()
            }
        }

        root.addView(title)
        root.addView(
            puppyView,
            LinearLayout.LayoutParams(
                -1,
                0,
                1f
            )
        )
        root.addView(status)
        root.addView(talk)

        setContentView(root)
    }

    override fun onInit(result: Int) {
        if (result == TextToSpeech.SUCCESS) {
            tts.language = Locale("fa", "IR")
            tts.setSpeechRate(0.85f)
        }
    }

    private fun requestMicrophone() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
        } else {
            startListening()
        }
    }

    private fun startListening() {
        status.text = "🎙️ اصغر و بچه‌ها دارن گوش میدن..."

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            speakFriendship()
            return
        }

        recognizer?.destroy()

        recognizer = SpeechRecognizer.createSpeechRecognizer(this)

        recognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}

            override fun onBeginningOfSpeech() {
                status.text = "👂 دارم گوش میدم..."
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {}

            override fun onError(error: Int) {
                speakFriendship()
            }

            override fun onResults(results: Bundle?) {
                speakFriendship()
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }

        recognizer?.startListening(intent)
    }

    private fun speakFriendship() {
        val message =
            "سیلوا و سلنا، من دوستتون دارم. با هم دعوا نکنید. " +
            "مثل ما با هم دوست باشید. مامانتون رو هم اذیت نکنید. آفرین!"

        status.text = "🐶❤️ اصغر، پامبول و مانگال دارن حرف میزنن..."

        tts.setPitch(1.15f)
        tts.setSpeechRate(0.85f)
        tts.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "asghar_message"
        )
    }

    override fun onDestroy() {
        recognizer?.destroy()
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}
