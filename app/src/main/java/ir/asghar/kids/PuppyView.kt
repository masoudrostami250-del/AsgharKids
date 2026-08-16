package ir.asghar.kids

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.math.sin

class PuppyView(context: Context) : View(context) {

    private val p = Paint(Paint.ANTI_ALIAS_FLAG)
    private var t = 0f

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)

        post(object : Runnable {
            override fun run() {
                t += 0.06f
                invalidate()
                postDelayed(this, 32)
            }
        })
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)

        val w = width.toFloat()
        val h = height.toFloat()

        // پس‌زمینه اتاق
        c.drawColor(Color.rgb(18, 25, 45))

        // پنجره
        p.style = Paint.Style.FILL
        p.color = Color.rgb(40, 65, 105)
        c.drawRoundRect(
            w * .04f, h * .04f,
            w * .34f, h * .30f,
            35f, 35f, p
        )

        p.color = Color.rgb(120, 190, 255)
        c.drawRect(w * .07f, h * .08f, w * .31f, h * .26f, p)

        // ستاره‌ها
        p.color = Color.WHITE
        for (i in 0 until 8) {
            val sx = w * (.08f + i * .028f)
            val sy = h * (.10f + (i % 4) * .035f)
            c.drawCircle(sx, sy, 3f, p)
        }

        // چراغ
        p.color = Color.rgb(255, 210, 100)
        c.drawCircle(w * .84f, h * .22f, 42f, p)

        // کف
        p.color = Color.rgb(120, 78, 52)
        c.drawRect(0f, h * .58f, w, h, p)

        // فرش
        p.color = Color.rgb(30, 70, 145)
        c.drawOval(
            w * .10f, h * .62f,
            w * .90f, h * .98f,
            p
        )

        // سایه فرش
        p.color = Color.argb(70, 0, 0, 0)
        c.drawOval(
            w * .14f, h * .76f,
            w * .86f, h * .99f,
            p
        )

        val bob = sin(t.toDouble()).toFloat() * 7f

        // اصغر
        drawPuppy(
            c,
            w * .50f,
            h * .48f + bob,
            1.28f,
            Color.rgb(180, 105, 50),
            Color.rgb(245, 222, 190),
            "اصغر",
            true
        )

        // پامبول
        drawPuppy(
            c,
            w * .25f,
            h * .66f - bob * .4f,
            .82f,
            Color.rgb(205, 135, 75),
            Color.rgb(250, 225, 195),
            "پامبول",
            false
        )

        // مانگال
        drawPuppy(
            c,
            w * .75f,
            h * .66f - bob * .4f,
            .82f,
            Color.rgb(65, 62, 70),
            Color.rgb(235, 225, 210),
            "مانگال",
            false
        )

        // حباب گفتگو
        p.color = Color.WHITE
        c.drawRoundRect(
            w * .39f, h * .035f,
            w * .95f, h * .18f,
            35f, 35f, p
        )

        p.color = Color.rgb(20, 35, 80)
        p.textAlign = Paint.Align.CENTER
        p.textSize = 18f
        c.drawText(
            "سلام دوست خوبم! 👋",
            w * .67f, h * .085f, p
        )
        c.drawText(
            "من اصغرم و این دوتا بچه‌هام",
            w * .67f, h * .125f, p
        )
        c.drawText(
            "پامبول و مانگال هستن 🐶",
            w * .67f, h * .165f, p
        )

        // منوهای سمت راست
        drawMenu(c, w * .79f, h * .25f, "🎮 بازی", Color.rgb(25, 100, 220))
        drawMenu(c, w * .79f, h * .34f, "📖 داستان", Color.rgb(115, 55, 210))
        drawMenu(c, w * .79f, h * .43f, "🎵 آهنگ", Color.rgb(225, 40, 100))
        drawMenu(c, w * .79f, h * .52f, "💡 آموزش", Color.rgb(245, 110, 20))

        // وضعیت سمت چپ
        drawStatus(c, w * .03f, h * .35f, "🎙️ دارم گوش میدم...", Color.rgb(25, 105, 220))
        drawStatus(c, w * .03f, h * .43f, "🧠 دارم فکر می‌کنم...", Color.rgb(120, 55, 220))
        drawStatus(c, w * .03f, h * .51f, "🔊 الان جواب میدم!", Color.rgb(20, 170, 90))

        // میکروفون بزرگ
        p.setShadowLayer(25f, 0f, 5f, Color.BLUE)
        p.color = Color.rgb(20, 85, 220)
        c.drawCircle(w * .50f, h * .79f, 58f, p)
        p.clearShadowLayer()

        p.color = Color.WHITE
        p.style = Paint.Style.STROKE
        p.strokeWidth = 10f
        c.drawRoundRect(
            w * .485f, h * .745f,
            w * .515f, h * .825f,
            18f, 18f, p
        )
        c.drawArc(
            w * .465f, h * .77f,
            w * .535f, h * .85f,
            0f, 180f, false, p
        )
        c.drawLine(
            w * .50f, h * .85f,
            w * .50f, h * .875f,
            p
        )
        p.style = Paint.Style.FILL

        // متن پایین
        p.color = Color.WHITE
        p.textSize = 17f
        c.drawText(
            "برای صحبت کردن لمس کن 👆",
            w * .50f,
            h * .94f,
            p
        )

        // انتخاب شخصیت‌ها
        drawProfile(c, w * .17f, h * .86f, "اصغر", Color.rgb(30, 105, 220))
        drawProfile(c, w * .30f, h * .86f, "پامبول", Color.rgb(230, 80, 140))
        drawProfile(c, w * .83f, h * .86f, "مانگال", Color.rgb(70, 90, 150))
    }

    private fun drawPuppy(
        c: Canvas,
        x: Float,
        y: Float,
        s: Float,
        fur: Int,
        muzzle: Int,
        name: String,
        adult: Boolean
    ) {
        val bounce = sin((t * 1.4f + x * .01f).toDouble()).toFloat() * 3f
        val yy = y + bounce

        // دم متحرک
        val tail = sin((t * 3f + x * .02f).toDouble()).toFloat() * 18f

        p.style = Paint.Style.STROKE
        p.strokeWidth = 24f * s
        p.strokeCap = Paint.Cap.ROUND
        p.color = fur

        val path = Path()
        path.moveTo(x + 55f * s, yy + 45f * s)
        path.cubicTo(
            x + 100f * s,
            yy + tail,
            x + 125f * s,
            yy - 35f * s,
            x + 82f * s,
            yy - 65f * s
        )
        c.drawPath(path, p)

        p.style = Paint.Style.FILL

        // بدن
        p.color = fur
        c.drawOval(
            x - 78f * s,
            yy - 5f * s,
            x + 78f * s,
            yy + 135f * s,
            p
        )

        // شکم روشن
        p.color = Color.argb(130, 255, 230, 190)
        c.drawOval(
            x - 47f * s,
            yy + 20f * s,
            x + 47f * s,
            yy + 120f * s,
            p
        )

        // گوش
        val ear = sin((t * 2f + x).toDouble()).toFloat() * 6f

        p.color = fur
        c.drawOval(
            x - 100f * s,
            yy - 105f * s + ear,
            x - 25f * s,
            yy + 20f * s + ear,
            p
        )
        c.drawOval(
            x + 25f * s,
            yy - 105f * s - ear,
            x + 100f * s,
            yy + 20f * s - ear,
            p
        )

        // سر
        p.color = fur
        c.drawCircle(x, yy - 50f * s, 88f * s, p)

        // هایلایت سر
        p.color = Color.argb(65, 255, 255, 255)
        c.drawCircle(
            x - 25f * s,
            yy - 80f * s,
            45f * s,
            p
        )

        // پوزه
        p.color = muzzle
        c.drawOval(
            x - 55f * s,
            yy - 25f * s,
            x + 55f * s,
            yy + 40f * s,
            p
        )

        // چشم
        p.color = Color.rgb(30, 22, 20)
        c.drawCircle(x - 34f * s, yy - 62f * s, 17f * s, p)
        c.drawCircle(x + 34f * s, yy - 62f * s, 17f * s, p)

        p.color = Color.WHITE
        c.drawCircle(x - 39f * s, yy - 68f * s, 6f * s, p)
        c.drawCircle(x + 29f * s, yy - 68f * s, 6f * s, p)

        // بینی
        p.color = Color.rgb(40, 25, 25)
        c.drawOval(
            x - 20f * s,
            yy - 10f * s,
            x + 20f * s,
            yy + 18f * s,
            p
        )

        // دهان متحرک
        val mouth = ((sin((t * 7f).toDouble()) + 1f) / 2f).toFloat()

        p.color = Color.rgb(95, 25, 35)
        c.drawOval(
            x - 27f * s,
            yy + 15f * s,
            x + 27f * s,
            yy + (38f + mouth * 20f) * s,
            p
        )

        // پنجه‌ها
        p.color = fur
        c.drawOval(
            x - 62f * s,
            yy + 85f * s,
            x - 15f * s,
            yy + 145f * s,
            p
        )
        c.drawOval(
            x + 15f * s,
            yy + 85f * s,
            x + 62f * s,
            yy + 145f * s,
            p
        )

        // دست اصغر
        if (adult) {
            val hand = sin((t * 2f).toDouble()).toFloat() * 18f

            c.save()
            c.rotate(-20f + hand, x - 75f * s, yy + 30f * s)
            c.drawOval(
                x - 120f * s,
                yy - 15f * s,
                x - 70f * s,
                yy + 65f * s,
                p
            )
            c.restore()
        }

        // پلاک
        p.color = when (name) {
            "اصغر" -> Color.rgb(20, 90, 210)
            "پامبول" -> Color.rgb(220, 70, 130)
            else -> Color.rgb(65, 85, 150)
        }

        c.drawRoundRect(
            x - 60f * s,
            yy + 125f * s,
            x + 60f * s,
            yy + 165f * s,
            18f * s,
            18f * s,
            p
        )

        p.color = Color.WHITE
        p.textAlign = Paint.Align.CENTER
        p.textSize = 18f * s
        c.drawText(name, x, yy + 151f * s, p)
    }

    private fun drawMenu(
        c: Canvas,
        x: Float,
        y: Float,
        text: String,
        color: Int
    ) {
        p.color = color
        p.setShadowLayer(12f, 0f, 3f, color)
        c.drawRoundRect(
            x - width * .18f,
            y,
            x + width * .18f,
            y + height * .065f,
            25f,
            25f,
            p
        )
        p.clearShadowLayer()

        p.color = Color.WHITE
        p.textAlign = Paint.Align.CENTER
        p.textSize = 18f
        c.drawText(text, x, y + height * .042f, p)
    }

    private fun drawStatus(
        c: Canvas,
        x: Float,
        y: Float,
        text: String,
        color: Int
    ) {
        p.color = color
        c.drawRoundRect(
            x,
            y,
            x + width * .29f,
            y + height * .06f,
            25f,
            25f,
            p
        )

        p.color = Color.WHITE
        p.textAlign = Paint.Align.CENTER
        p.textSize = 14f
        c.drawText(
            text,
            x + width * .145f,
            y + height * .039f,
            p
        )
    }

    private fun drawProfile(
        c: Canvas,
        x: Float,
        y: Float,
        name: String,
        color: Int
    ) {
        p.color = color
        c.drawCircle(x, y, 34f, p)

        p.color = Color.WHITE
        p.textAlign = Paint.Align.CENTER
        p.textSize = 13f
        c.drawText(name, x, y + 52f, p)
    }
}
