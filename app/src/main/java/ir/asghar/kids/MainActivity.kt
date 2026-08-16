package ir.asghar.kids

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.rgb(255, 243, 224))
        }

        val title = TextView(this).apply {
            text = "🐶 اصغر"
            textSize = 42f
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD)
        }

        val welcome = TextView(this).apply {
            text = "سلام! من اصغرم 😄\\nدوست داری با من حرف بزنی؟"
            textSize = 24f
            gravity = Gravity.CENTER
            setPadding(0, 30, 0, 40)
        }

        val talkButton = TextView(this).apply {
            text = "🎙️  با اصغر حرف بزن"
            textSize = 22f
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(255, 152, 0))
            setPadding(40, 25, 40, 25)

            setOnClickListener {
                requestMicrophone()
            }
        }

        statusText = TextView(this).apply {
            text = "اصغر منتظرته! 🐶"
            textSize = 18f
            gravity = Gravity.CENTER
            setPadding(0, 35, 0, 0)
        }

        root.addView(title)
        root.addView(welcome)
        root.addView(talkButton)
        root.addView(statusText)

        setContentView(root)
    }

    private fun requestMicrophone() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    100
                )
            } else {
                startListening()
            }
        } else {
            startListening()
        }
    }

    private fun startListening() {
        statusText.text = "🎙️ اصغر داره گوش میده..."
        Toast.makeText(
            this,
            "میکروفون آماده است",
            Toast.LENGTH_SHORT
        ).show()
    }
}
