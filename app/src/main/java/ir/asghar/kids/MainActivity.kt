package ir.asghar.kids

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.content.Intent
import android.graphics.Color
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var speechRecognizer: SpeechRecognizer
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)

        val root = FrameLayout(this).apply {
            setBackgroundColor(Color.rgb(248, 226, 198))
        }

        val puppyView = PuppyView(this)

        root.addView(
            puppyView,
            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        setContentView(root)

        setupSpeech()
    }

    private fun setupSpeech() {

        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            return
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        speechRecognizer.setRecognitionListener(
            object : RecognitionListener {

                override fun onReadyForSpeech(params: Bundle?) {}

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(
                    eventType: Int,
                    params: Bundle?
                ) {}

                override fun onError(error: Int) {
                    Toast.makeText(
                        this@MainActivity,
                        "اصغر دوباره گوش میده 🐶",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResults(results: Bundle?) {

                    val matches =
                        results?.getStringArrayList(
                            SpeechRecognizer.RESULTS_RECOGNITION
                        )

                    val text = matches?.firstOrNull()

                    if (!text.isNullOrBlank()) {
                        answerAsghar(text)
                    }
                }
            }
        )
    }

    private fun startListening() {

        if (checkSelfPermission(
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )

            return
        }

        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                "fa-IR"
            )

            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                "اصغر گوش میده..."
            )
        }

        speechRecognizer.startListening(intent)
    }

    private fun answerAsghar(text: String) {

        val answer = when {

            text.contains("اصغر") ->
                "جانم! من اصغرم 🐶❤️"

            text.contains("سلام") ->
                "سلام! خیلی خوشحالم که با من حرف می‌زنی 😄"

            text.contains("پامبول") ->
                "پامبول اینجاست! 🐶"

            text.contains("مانگال") ->
                "مانگال هم اینجاست! 🐶"

            text.contains("دعوا") ->
                "سیلوا و سلنا، با هم دعوا نکنید. با هم دوست باشید و مامانتون رو اذیت نکنید. آفرین ❤️"

            text.contains("دوستت دارم") ->
                "من هم دوستتون دارم ❤️"

            else ->
                "آفرین! اصغر صدات رو شنید 🐶"
        }

        speak(answer)
    }

    private fun speak(text: String) {

        tts.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "asghar"
        )
    }

    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {

            tts.language = Locale("fa", "IR")

            tts.setSpeechRate(0.9f)
        }
    }

    override fun onDestroy() {

        if (::speechRecognizer.isInitialized) {
            speechRecognizer.destroy()
        }

        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }

        super.onDestroy()
    }
}
