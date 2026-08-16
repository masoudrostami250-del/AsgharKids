package ir.asghar.kids

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import kotlin.math.sin

class PuppyView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bitmap: Bitmap? =
        BitmapFactory.decodeResource(
            resources,
            R.drawable.asghar_home
        )

    private var time = 0.0

    private val animator = object : Runnable {
        override fun run() {
            time += 0.05
            invalidate()
            postDelayed(this, 16)
        }
    }

    init {
        post(animator)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(animator)
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val image = bitmap ?: return

        val w = width.toFloat()
        val h = height.toFloat()

        if (w <= 0f || h <= 0f) return

        /*
         * حرکت آرام بالا و پایین
         */
        val floatY = (sin(time) * 8.0).toFloat()

        /*
         * نفس کشیدن / زوم خیلی ملایم
         */
        val scalePulse = (1.0 + sin(time * 0.8) * 0.015).toFloat()

        /*
         * تصویر را بدون کشیدگی در صفحه جا می‌دهیم
         */
        val imageRatio = image.width.toFloat() / image.height.toFloat()
        val viewRatio = w / h

        var drawWidth: Float
        var drawHeight: Float

        if (imageRatio > viewRatio) {
            drawWidth = w
            drawHeight = w / imageRatio
        } else {
            drawHeight = h
            drawWidth = h * imageRatio
        }

        val left = (w - drawWidth) / 2f
        val top = (h - drawHeight) / 2f

        canvas.save()

        /*
         * مرکز تصویر
         */
        val cx = w / 2f
        val cy = h / 2f + floatY

        canvas.translate(cx, cy)
        canvas.scale(scalePulse, scalePulse)
        canvas.translate(-cx, -cy)

        val dstLeft = left
        val dstTop = top + floatY
        val dstRight = left + drawWidth
        val dstBottom = top + drawHeight + floatY

        canvas.drawBitmap(
            image,
            null,
            android.graphics.RectF(
                dstLeft,
                dstTop,
                dstRight,
                dstBottom
            ),
            paint
        )

        canvas.restore()

        /*
         * یک درخشش بسیار ملایم متحرک برای زنده‌تر شدن صحنه
         */
        val glowAlpha =
            (25 + ((sin(time * 1.5) + 1.0) * 15.0)).toInt()

        paint.color = android.graphics.Color.argb(
            glowAlpha,
            30,
            120,
            255
        )

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f

        val pulse =
            (sin(time * 1.5) * 8.0 + 8.0).toFloat()

        canvas.drawCircle(
            w / 2f,
            h * 0.79f,
            70f + pulse,
            paint
        )

        paint.style = Paint.Style.FILL
    }
}
