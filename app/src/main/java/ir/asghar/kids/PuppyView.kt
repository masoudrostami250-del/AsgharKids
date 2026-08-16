package ir.asghar.kids

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.sin

class PuppyView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var time = 0f
    private var wave = 0f

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        post(object : Runnable {
            override fun run() {
                time += 0.08f
                wave += 0.12f
                invalidate()
                postDelayed(this, 32)
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.rgb(255, 243, 224))

        // زمین
        paint.color = Color.rgb(210, 235, 255)
        canvas.drawOval(
            width * .08f,
            height * .72f,
            width * .92f,
            height * .98f,
            paint
        )

        val bob = sin(time.toDouble()).toFloat() * 8f

        drawPuppy(
            canvas,
            width * .50f,
            height * .48f + bob,
            1.25f,
            Color.rgb(190, 120, 65),
            Color.WHITE,
            "اصغر",
            false
        )

        drawPuppy(
            canvas,
            width * .25f,
            height * .65f - bob * .5f,
            .82f,
            Color.rgb(210, 145, 90),
            Color.WHITE,
            "پامبول",
            true
        )

        drawPuppy(
            canvas,
            width * .75f,
            height * .65f - bob * .5f,
            .82f,
            Color.rgb(70, 70, 80),
            Color.WHITE,
            "مانگال",
            true
        )
    }

    private fun drawPuppy(
        c: Canvas,
        x: Float,
        y: Float,
        s: Float,
        fur: Int,
        muzzle: Int,
        name: String,
        child: Boolean
    ) {
        // دم متحرک
        paint.color = fur
        paint.strokeWidth = 28f * s
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND

        val tailMove = sin((time * 2.0 + x).toDouble()).toFloat() * 22f

        val tail = Path()
        tail.moveTo(x + 55f * s, y + 45f * s)
        tail.cubicTo(
            x + 100f * s,
            y + tailMove,
            x + 115f * s,
            y - 20f * s,
            x + 85f * s,
            y - 45f * s
        )
        c.drawPath(tail, paint)

        paint.style = Paint.Style.FILL

        // بدن
        c.drawOval(
            x - 75f * s,
            y - 5f * s,
            x + 75f * s,
            y + 125f * s,
            paint
        )

        // گوش‌ها
        val earMove = sin((time * 1.5 + x).toDouble()).toFloat() * 7f

        c.drawOval(
            x - 95f * s,
            y - 105f * s + earMove,
            x - 25f * s,
            y + 15f * s + earMove,
            paint
        )

        c.drawOval(
            x + 25f * s,
            y - 105f * s - earMove,
            x + 95f * s,
            y + 15f * s - earMove,
            paint
        )

        // سر
        c.drawCircle(x, y - 45f * s, 82f * s, paint)

        // پوزه
        paint.color = muzzle
        c.drawOval(
            x - 50f * s,
            y - 30f * s,
            x + 50f * s,
            y + 35f * s,
            paint
        )

        // چشم‌ها
        paint.color = Color.rgb(35, 25, 20)

        val blink = sin((time * .65 + x).toDouble()).toFloat() > .97f
        if (blink) {
            paint.strokeWidth = 8f * s
            paint.style = Paint.Style.STROKE
            c.drawLine(
                x - 45f * s, y - 58f * s,
                x - 20f * s, y - 58f * s,
                paint
            )
            c.drawLine(
                x + 20f * s, y - 58f * s,
                x + 45f * s, y - 58f * s,
                paint
            )
            paint.style = Paint.Style.FILL
        } else {
            c.drawCircle(x - 32f * s, y - 58f * s, 14f * s, paint)
            c.drawCircle(x + 32f * s, y - 58f * s, 14f * s, paint)

            paint.color = Color.WHITE
            c.drawCircle(x - 36f * s, y - 63f * s, 5f * s, paint)
            c.drawCircle(x + 28f * s, y - 63f * s, 5f * s, paint)
        }

        // بینی
        paint.color = Color.rgb(45, 30, 30)
        c.drawOval(
            x - 18f * s,
            y - 10f * s,
            x + 18f * s,
            y + 15f * s,
            paint
        )

        // دهان متحرک
        val mouth = (sin(time * 5f + x) + 1f) / 2f
        paint.color = Color.rgb(100, 35, 45)

        c.drawOval(
            x - 25f * s,
            y + 10f * s,
            x + 25f * s,
            y + (28f + mouth * 22f) * s,
            paint
        )

        // پنجه
        paint.color = fur
        c.drawOval(
            x - 60f * s,
            y + 75f * s,
            x - 15f * s,
            y + 135f * s,
            paint
        )

        c.drawOval(
            x + 15f * s,
            y + 75f * s,
            x + 60f * s,
            y + 135f * s,
            paint
        )

        // پلاک اسم
        paint.color = when (name) {
            "اصغر" -> Color.rgb(30, 100, 210)
            "پامبول" -> Color.rgb(240, 80, 130)
            else -> Color.rgb(80, 100, 150)
        }

        c.drawRoundRect(
            x - 55f * s,
            y + 125f * s,
            x + 55f * s,
            y + 165f * s,
            18f * s,
            18f * s,
            paint
        )

        paint.color = Color.WHITE
        paint.textSize = 19f * s
        paint.textAlign = Paint.Align.CENTER
        c.drawText(name, x, y + 152f * s, paint)
    }
}
