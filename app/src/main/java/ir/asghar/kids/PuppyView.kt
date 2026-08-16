package ir.asghar.kids

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import kotlin.math.sin

class PuppyView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    private val asghar: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.asghar)

    private val pambool: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.pambool)

    private val mangal: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.mangal)

    private var time = 0f

    private val animator = object : Runnable {
        override fun run() {
            time += 0.045f
            invalidate()
            postDelayed(this, 16L)
        }
    }

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        post(animator)
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(animator)
        super.onDetachedFromWindow()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        if (w <= 0f || h <= 0f) return

        // پس‌زمینه ساده و زنده
        canvas.drawColor(android.graphics.Color.rgb(8, 12, 28))

        /*
         * سه سگ کاملاً مستقل هستند.
         *
         * هر کدام:
         * - بالا و پایین می‌رود
         * - کمی به چپ و راست تکان می‌خورد
         * - هنگام صحبت کمی بزرگ و کوچک می‌شود
         */

        val talkingAsghar = ((time * 1.8f).toInt() % 5 == 0)
        val talkingPambool = ((time * 1.55f + 1.7f).toInt() % 6 == 0)
        val talkingMangal = ((time * 1.65f + 3.0f).toInt() % 7 == 0)

        // اصغر - شخصیت اصلی
        drawCharacter(
            canvas = canvas,
            bitmap = asghar,
            centerX = w * 0.50f,
            centerY = h * 0.58f,
            baseWidth = w * 0.40f,
            bob = sin(time * 2.2f) * 12f,
            sway = sin(time * 1.7f) * 5f,
            talking = talkingAsghar
        )

        // پامبول - سمت چپ
        drawCharacter(
            canvas = canvas,
            bitmap = pambool,
            centerX = w * 0.25f,
            centerY = h * 0.67f,
            baseWidth = w * 0.27f,
            bob = sin(time * 2.7f + 1.2f) * 9f,
            sway = sin(time * 2.0f + 0.8f) * 4f,
            talking = talkingPambool
        )

        // مانگال - سمت راست
        drawCharacter(
            canvas = canvas,
            bitmap = mangal,
            centerX = w * 0.75f,
            centerY = h * 0.67f,
            baseWidth = w * 0.28f,
            bob = sin(time * 2.5f + 2.4f) * 10f,
            sway = sin(time * 1.9f + 2f) * 4f,
            talking = talkingMangal
        )

        // میکروفون متحرک پایین صفحه
        drawMicrophone(canvas, w, h)
    }

    private fun drawCharacter(
        canvas: Canvas,
        bitmap: Bitmap,
        centerX: Float,
        centerY: Float,
        baseWidth: Float,
        bob: Float,
        sway: Float,
        talking: Boolean
    ) {
        val ratio = bitmap.height.toFloat() / bitmap.width.toFloat()

        var width = baseWidth
        var height = width * ratio

        // هنگام حرف زدن، بدن کمی squash/stretch می‌شود.
        if (talking) {
            val pulse = (sin(time * 15f) * 0.035f).toFloat()
            width *= 1f + pulse
            height *= 1f - pulse * 0.45f
        }

        val left = centerX - width / 2f + sway
        val top = centerY - height / 2f + bob

        val dst = RectF(
            left,
            top,
            left + width,
            top + height
        )

        canvas.save()

        // چرخش بسیار کم برای طبیعی شدن حرکت
        val rotation = sin(time * 1.8f + centerX) * 1.8f
        canvas.rotate(
            rotation,
            centerX + sway,
            centerY + bob
        )

        paint.alpha = 255

        canvas.drawBitmap(
            bitmap,
            null,
            dst,
            paint
        )

        canvas.restore()

        // هاله کوچک زیر شخصیت برای حس زنده بودن
        paint.style = Paint.Style.FILL
        paint.alpha = 45

        canvas.drawOval(
            RectF(
                centerX - width * 0.32f,
                top + height * 0.91f,
                centerX + width * 0.32f,
                top + height * 0.98f
            ),
            paint
        )

        paint.alpha = 255
    }

    private fun drawMicrophone(
        canvas: Canvas,
        w: Float,
        h: Float
    ) {
        val cx = w / 2f
        val cy = h * 0.91f

        val pulse =
            1f + sin(time * 5f) * 0.08f

        canvas.save()
        canvas.scale(pulse, pulse, cx, cy)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = android.graphics.Color.WHITE

        val micTop = cy - 55f
        val micBottom = cy + 25f

        canvas.drawRoundRect(
            RectF(
                cx - 20f,
                micTop,
                cx + 20f,
                micBottom
            ),
            20f,
            20f,
            paint
        )

        canvas.drawArc(
            RectF(
                cx - 38f,
                cy - 5f,
                cx + 38f,
                cy + 55f
            ),
            0f,
            180f,
            false,
            paint
        )

        canvas.drawLine(
            cx,
            cy + 55f,
            cx,
            cy + 78f,
            paint
        )

        paint.style = Paint.Style.FILL

        canvas.restore()
    }
}
