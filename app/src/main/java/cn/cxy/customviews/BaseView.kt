package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

open class BaseView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var defaultSize = 200
    protected var mWidth = defaultSize
    protected var mHeight = defaultSize

    init {
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = startMeasure(widthMeasureSpec)
        mHeight = startMeasure(heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun startMeasure(msSpec: Int): Int {
        val mode = MeasureSpec.getMode(msSpec)
        val size = MeasureSpec.getSize(msSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            dpToPx(context, defaultSize)
        }
    }

    protected fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
//
//        canvas.drawCircle(centerX, centerY, radius, mPaint)
//        // 隔一段时间重绘一次, 动画效果
////        handler.postDelayed(runnable, intervalTime.toLong())
//    }

//    //当前是否处于放大过程中
//    var isIncreasing = false
//
//    // 重绘线程
//    private val runnable = Runnable {
//        smoothChangeRadiusSize()
//        invalidate()
//    }

    /**
     * 先缩小，后放大，营造呼吸效果
     */
//    private fun smoothChangeRadiusSize() {
//        if (isIncreasing) {
//            minSize++
//        } else {
//            minSize--
//        }
//        if (minSize <= 0) {
//            minSize++
//            isIncreasing = true
//        } else if (minSize >= minValue(mWidth, mHeight)) {
//            minSize--
//            isIncreasing = false
//        }
//        setRadiusSizes(minSize)
//    }
//
//    private fun setRadiusSizes(baseSize: Int) {
//        radius = baseSize / 4.toFloat()
//        blurRadius = baseSize / 5.toFloat()
//    }
}