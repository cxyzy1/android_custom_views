package cn.cxy.customviews.lightspot

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import cn.cxy.customviews.dp2Px
import cn.cxy.customviews.minValue

class LightSpotView4(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mPaint = Paint()
    private var centerX = 0F
    private var centerY = 0F
    private var defaultSize = 200F

    //圆点大小
    private var radius = 50F

    //发光范围
    private var radioRadius = 50F

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint.color = Color.parseColor("#FFFFFF")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val realWidth = startMeasure(widthMeasureSpec)
        val realHeight = startMeasure(heightMeasureSpec)
        setMeasuredDimension(realWidth, realHeight)
        centerX = realWidth / 2.toFloat()
        centerY = realHeight / 2.toFloat()

        val baseSize = minValue(realWidth, realHeight)
        radioRadius = baseSize / 5.toFloat()
        radius = baseSize / 4.toFloat()
    }

    private fun startMeasure(msSpec: Int): Int {
        val mode = MeasureSpec.getMode(msSpec)
        val size = MeasureSpec.getSize(msSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            dp2Px( context,defaultSize).toInt()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.maskFilter = BlurMaskFilter(radioRadius, BlurMaskFilter.Blur.NORMAL)

        canvas.drawCircle(centerX, centerY, radius, mPaint)
    }
}