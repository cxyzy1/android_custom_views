package cn.cxy.customviews.lightspot

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class LightSpotView2(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var lightPaint = Paint()
    private var centerX = 0
    private var centerY = 0
    private var defaultSize = 200


    /** 发光范围  */
    private var radioRadius = 50

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        lightPaint.color = Color.parseColor("#FFFFFF")
        lightPaint.maskFilter = BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.INNER)
    }

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
            dpToPx(defaultSize, context)
        }
    }

    private fun dpToPx(dp: Int, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    var mBlurType:Int=0
    fun setBlurType(blurType: Int) {
        mBlurType = blurType

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        radioRadius = width/5
        when (mBlurType) {
            0 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.INNER)
            1 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.NORMAL)
            2 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.SOLID)
            3 -> lightPaint.maskFilter =
                BlurMaskFilter(radioRadius.toFloat(), BlurMaskFilter.Blur.OUTER)
        }

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), width/4.toFloat(), lightPaint)
    }
}