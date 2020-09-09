package cn.cxy.customviews.lightspot

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class LightSpotView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var lightPaint = Paint()
    private var centerX = 0
    private var centerY = 0

    /** 发光范围  */
    private val radioRadius = 50

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        lightPaint.color = Color.parseColor("#FFFFFF")
        lightPaint.maskFilter = BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.INNER)
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
//        val height = measureSize(defaultHeight, heightMeasureSpec)
//        val width = measureSize(defaultWidth, widthMeasureSpec)
//        setMeasuredDimension(width, height)
//        mViewWidth = width
//        mViewHeight = height
//    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        centerX = left + measuredWidth / 2
        centerY = top + measuredHeight / 2
        val realWidth = startMeasure(widthMeasureSpec)
        val realHeight = startMeasure(heightMeasureSpec)
        setMeasuredDimension(realWidth, realHeight)
    }

    private fun startMeasure(msSpec: Int): Int {
        val mode = MeasureSpec.getMode(msSpec)
        val size = MeasureSpec.getSize(msSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            dpToPx(200, context)
        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    fun setBlurType(blurType: Int) {
        when (blurType) {
            0 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.INNER)
            1 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.NORMAL)
            2 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.SOLID)
            3 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.OUTER)
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), 30f, lightPaint)
    }
}