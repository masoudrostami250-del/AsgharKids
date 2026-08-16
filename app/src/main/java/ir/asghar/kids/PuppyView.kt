package ir.asghar.kids

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View

class PuppyView(context: Context) : View(context) {

    private val bitmap =
        BitmapFactory.decodeResource(
            resources,
            R.drawable.asghar_home
        )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (bitmap == null) return

        val src = Rect(
            0,
            0,
            bitmap.width,
            bitmap.height
        )

        val dst = Rect(
            0,
            0,
            width,
            height
        )

        canvas.drawBitmap(
            bitmap,
            src,
            dst,
            null
        )
    }
}
