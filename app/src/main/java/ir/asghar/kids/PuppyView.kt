package ir.asghar.kids

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.sin

class PuppyView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var time = 0f

    init {
        post(object : Runnable {
            override fun run() {
                time += 0.05f
                invalidate()
                postDelayed(this, 33)
            }
        })
    }

    override fun onDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()

        // پس‌زمینه
        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(248, 226, 198)
        canvas.drawRect(0f, 0f, w, h, paint)

        drawRoom(canvas, w, h)

        // سه شخصیت اصلی
        drawPuppy(
            canvas,
            w * .50f,
            h * .47f,
            1.25f,
            Color.rgb(181, 116, 67),
            "اصغر",
            true
        )

        drawPuppy(
            canvas,
            w * .25f,
            h * .65f,
            .82f,
            Color.rgb(218, 157, 91),
            "پامبول",
            false
        )

        drawPuppy(
            canvas,
            w * .75f,
            h * .65f,
            .82f,
            Color.rgb(76, 76, 84),
            "مانگال",
            false
        )

        drawSpeech(canvas, w, h)
        drawRightButtons(canvas, w, h)
        drawBottomBar(canvas, w, h)
        drawMicrophone(canvas, w, h)
    }

    private fun drawRoom(c: Canvas, w: Float, h: Float) {

        // دیوار
        paint.color = Color.rgb(255, 241, 218)
        c.drawRect(0f, 0f, w, h * .61f, paint)

        // قاب‌های رنگی
        paint.color = Color.rgb(255, 196, 91)
        c.drawRoundRect(
            w * .04f, h * .08f,
            w * .31f, h * .28f,
            28f, 28f, paint
        )

        paint.color = Color.rgb(103, 182, 220)
        c.drawRoundRect(
            w * .07f, h * .11f,
            w * .28f, h * .25f,
            20f, 20f, paint
        )

        // ابر و خورشید
        paint.color = Color.rgb(255, 215, 90)
        c.drawCircle(w * .20f, h * .17f, 25f, paint)

        paint.color = Color.WHITE
        c.drawCircle(w * .13f, h * .14f, 17f, paint)
        c.drawCircle(w * .16f, h * .13f, 23f, paint)
        c.drawCircle(w * .20f, h * .15f, 17f, paint)

        // قفسه
        paint.color = Color.rgb(168, 103, 58)
        c.drawRoundRect(
            w * .36f, h * .06f,
            w * .68f, h * .22f,
            20f, 20f, paint
        )

        paint.color = Color.rgb(250, 206, 100)
        c.drawRect(w * .39f, h * .09f, w * .65f, h * .12f, paint)
        c.drawRect(w * .39f, h * .16f, w * .65f, h * .19f, paint)

        // اسباب‌بازی‌ها
        paint.color = Color.rgb(231, 85, 106)
        c.drawCircle(w * .43f, h * .14f, 12f, paint)

        paint.color = Color.rgb(84, 147, 224)
        c.drawCircle(w * .53f, h * .14f, 13f, paint)

        paint.color = Color.rgb(90, 190, 120)
        c.drawCircle(w * .61f, h * .14f, 11f, paint)

        // کف
        paint.color = Color.rgb(186, 127, 83)
        c.drawRect(0f, h * .60f, w, h, paint)

        // فرش
        paint.color = Color.rgb(79, 126, 204)
        c.drawOval(
            w * .08f, h * .66f,
            w * .92f, h * .98f,
            paint
        )

        // الگوی فرش
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.color = Color.argb(110, 255, 255, 255)

        c.drawOval(
            w * .16f, h * .71f,
            w * .84f, h * .93f,
            paint
        )

        paint.style = Paint.Style.FILL
    }

    private fun drawPuppy(
        c: Canvas,
        x: Float,
        y: Float,
        scale: Float,
        fur: Int,
        name: String,
        adult: Boolean
    ) {

        val bounce =
            sin((time * 1.8f + x * .01f).toDouble()).toFloat() * 5f

        val yy = y + bounce

        // دم
        val tailWave =
            sin((time * 3.2f + x * .02f).toDouble()).toFloat() * 25f

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 24f * scale
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = fur

        val tail = Path()
        tail.moveTo(
            x + 55f * scale,
            yy + 45f * scale
        )
        tail.cubicTo(
            x + 105f * scale,
            yy + tailWave,
            x + 125f * scale,
            yy - 45f * scale,
            x + 80f * scale,
            yy - 65f * scale
        )

        c.drawPath(tail, paint)

        paint.style = Paint.Style.FILL

        // بدن
        paint.color = fur
        c.drawOval(
            x - 76f * scale,
            yy - 5f * scale,
            x + 76f * scale,
            yy + 140f * scale,
            paint
        )

        // شکم
        paint.color = Color.rgb(247, 220, 183)
        c.drawOval(
            x - 47f * scale,
            yy + 20f * scale,
            x + 47f * scale,
            yy + 120f * scale,
            paint
        )

        // گوش‌ها
        val earMove =
            sin((time * 2.1f + x).toDouble()).toFloat() * 8f

        paint.color = fur

        c.drawOval(
            x - 105f * scale,
            yy - 110f * scale + earMove,
            x - 28f * scale,
            yy + 22f * scale + earMove,
            paint
        )

        c.drawOval(
            x + 28f * scale,
            yy - 110f * scale - earMove,
            x + 105f * scale,
            yy + 22f * scale - earMove,
            paint
        )

        // سر
        c.drawCircle(
            x,
            yy - 55f * scale,
            91f * scale,
            paint
        )

        // پوزه
        paint.color = Color.rgb(249, 225, 194)

        c.drawOval(
            x - 55f * scale,
            yy - 23f * scale,
            x + 55f * scale,
            yy + 42f * scale,
            paint
        )

        // چشم‌ها
        paint.color = Color.rgb(38, 28, 25)

        val blink =
            sin((time * .8f).toDouble()) > .94

        if (!blink) {
            c.drawOval(
                x - 52f * scale,
                yy - 73f * scale,
                x - 18f * scale,
                yy - 38f * scale,
                paint
            )

            c.drawOval(
                x + 18f * scale,
                yy - 73f * scale,
                x + 52f * scale,
                yy - 38f * scale,
                paint
            )

            paint.color = Color.WHITE

            c.drawCircle(
                x - 43f * scale,
                yy - 65f * scale,
                7f * scale,
                paint
            )

            c.drawCircle(
                x + 27f * scale,
                yy - 65f * scale,
                7f * scale,
                paint
            )
        } else {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 5f * scale

            c.drawLine(
                x - 51f * scale,
                yy - 55f * scale,
                x - 20f * scale,
                yy - 55f * scale,
                paint
            )

            c.drawLine(
                x + 20f * scale,
                yy - 55f * scale,
                x + 51f * scale,
                yy - 55f * scale,
                paint
            )

            paint.style = Paint.Style.FILL
        }

        // بینی
        paint.color = Color.rgb(48, 30, 28)

        c.drawOval(
            x - 20f * scale,
            yy - 9f * scale,
            x + 20f * scale,
            yy + 18f * scale,
            paint
        )

        // دهان متحرک
        val talking =
            (sin((time * 7f).toDouble()) + 1f) / 2f

        paint.color = Color.rgb(103, 29, 38)

        c.drawOval(
            x - 28f * scale,
            yy + 15f * scale,
            x + 28f * scale,
            yy + (34f + talking * 22f).toFloat() * scale,
            paint
        )

        // زبان
        if (talking > .75f) {
            paint.color = Color.rgb(245, 110, 130)

            c.drawOval(
                x - 14f * scale,
                yy + 30f * scale,
                x + 14f * scale,
                yy + 55f * scale,
                paint
            )
        }

        // پاها
        paint.color = fur

        c.drawOval(
            x - 64f * scale,
            yy + 90f * scale,
            x - 12f * scale,
            yy + 155f * scale,
            paint
        )

        c.drawOval(
            x + 12f * scale,
            yy + 90f * scale,
            x + 64f * scale,
            yy + 155f * scale,
            paint
        )

        // دست متحرک اصغر
        if (adult) {
            val wave =
                sin((time * 2.5f).toDouble()).toFloat() * 22f

            c.save()

            c.rotate(
                -20f + wave,
                x - 72f * scale,
                yy + 35f * scale
            )

            c.drawOval(
                x - 125f * scale,
                yy - 10f * scale,
                x - 72f * scale,
                yy + 75f * scale,
                paint
            )

            c.restore()
        }

        // پلاک اسم
        paint.color = when (name) {
            "اصغر" -> Color.rgb(25, 94, 205)
            "پامبول" -> Color.rgb(226, 79, 132)
            else -> Color.rgb(72, 87, 151)
        }

        c.drawRoundRect(
            x - 62f * scale,
            yy + 130f * scale,
            x + 62f * scale,
            yy + 170f * scale,
            20f,
            20f,
            paint
        )

        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 18f * scale

        c.drawText(
            name,
            x,
            yy + 157f * scale,
            paint
        )
    }

    private fun drawSpeech(c: Canvas, w: Float, h: Float) {

        drawBubble(
            c,
            w * .37f,
            h * .025f,
            w * .95f,
            h * .13f,
            "سلام سیلوا و سلنا ❤️"
        )

        drawBubble(
            c,
            w * .04f,
            h * .30f,
            w * .33f,
            h * .39f,
            "دعوا نکنید! 🐶"
        )
    }

    private fun drawBubble(
        c: Canvas,
        l: Float,
        top: Float,
        r: Float,
        b: Float,
        text: String
    ) {
        paint.color = Color.WHITE

        c.drawRoundRect(
            l,
            top,
            r,
            b,
            25f,
            25f,
            paint
        )

        paint.color = Color.rgb(35, 45, 75)
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 17f

        c.drawText(
            text,
            (l + r) / 2f,
            (top + b) / 2f + 6f,
            paint
        )
    }

    private fun drawRightButtons(c: Canvas, w: Float, h: Float) {

        val labels = arrayOf(
            "🎮 بازی",
            "📖 داستان",
            "🎵 آهنگ",
            "💡 آموزش",
            "🐾 حیوانات",
            "🌙 خواب"
        )

        val colors = arrayOf(
            Color.rgb(42, 105, 220),
            Color.rgb(119, 72, 205),
            Color.rgb(224, 70, 119),
            Color.rgb(238, 125, 38),
            Color.rgb(35, 165, 115),
            Color.rgb(65, 75, 150)
        )

        for (i in labels.indices) {

            val y = h * (.18f + i * .075f)

            paint.color = colors[i]

            c.drawRoundRect(
                w * .76f,
                y,
                w * .97f,
                y + h * .06f,
                22f,
                22f,
                paint
            )

            paint.color = Color.WHITE
            paint.textAlign = Paint.Align.CENTER
            paint.textSize = 16f

            c.drawText(
                labels[i],
                w * .865f,
                y + h * .039f,
                paint
            )
        }
    }

    private fun drawMicrophone(
        c: Canvas,
        w: Float,
        h: Float
    ) {

        val pulse =
            (sin((time * 3f).toDouble()) + 1f).toFloat() * 5f

        paint.color = Color.argb(
            45,
            30,
            100,
            255
        )

        c.drawCircle(
            w * .50f,
            h * .79f,
            72f + pulse,
            paint
        )

        paint.color = Color.rgb(25, 95, 220)

        c.drawCircle(
            w * .50f,
            h * .79f,
            55f,
            paint
        )

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 9f
        paint.color = Color.WHITE

        c.drawRoundRect(
            w * .485f,
            h * .75f,
            w * .515f,
            h * .83f,
            15f,
            15f,
            paint
        )

        c.drawArc(
            w * .465f,
            h * .77f,
            w * .535f,
            h * .85f,
            0f,
            180f,
            false,
            paint
        )

        c.drawLine(
            w * .50f,
            h * .85f,
            w * .50f,
            h * .88f,
            paint
        )

        paint.style = Paint.Style.FILL

        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 16f
        c.drawText(
            "با اصغر حرف بزن",
            w * .50f,
            h * .94f,
            paint
        )
    }

    private fun drawBottomBar(
        c: Canvas,
        w: Float,
        h: Float
    ) {

        paint.color = Color.argb(235, 255, 255, 255)

        c.drawRoundRect(
            w * .03f,
            h * .955f,
            w * .97f,
            h,
            25f,
            25f,
            paint
        )

        val items = arrayOf(
            "🏠",
            "💬",
            "❤️",
            "👨‍👩‍👧",
            "⚙️"
        )

        paint.textAlign = Paint.Align.CENTER
        paint.textSize = 25f

        for (i in items.indices) {
            c.drawText(
                items[i],
                w * (.12f + i * .19f),
                h * .988f,
                paint
            )
        }
    }
}
