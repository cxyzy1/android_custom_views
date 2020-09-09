package cn.cxy.customviews.lightspot

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class LightSpotView3(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mPaint = Paint()

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint.color = Color.parseColor("#FFFFFF")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //发光范围
        val radioRadius = width / 5.toFloat()
        mPaint.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.NORMAL)

        //圆点大小
        val radius = width / 4.toFloat()
        canvas.drawCircle(width / 2.toFloat(), height / 2.toFloat(), radius, mPaint)
    }
}