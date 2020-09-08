package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 * 星形，带模糊效果,带动画
 */
class StarViewWithAnimation(context: Context, attrs: AttributeSet? = null) :
    BaseView(context, attrs) {
    private val mPaint = Paint()
    private val strokeWidth = dpToPx(context, 4).toFloat()
    private val path = Path()
    private val intervalTime = 5 //重绘间隔时间

    init {
        //设置实心
        mPaint.style = Paint.Style.FILL
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = strokeWidth
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStar(canvas)
    }

    private fun drawStar(canvas: Canvas) {
        setPaintStyle(true)
        percentValue = 50
        var floatPercent = percentValue / 100.toFloat()

        val blurRadius = width* floatPercent / 20.toFloat()
        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        val xxSize = dpToPx(context, 5)
        path.moveTo(width / 2.toFloat(), height / 2.toFloat() * (1 - floatPercent))
        path.lineTo(width / 2.toFloat() + xxSize, height.toFloat() / 2 - xxSize)
        path.lineTo(width / 2.toFloat() + width / 2.toFloat() * floatPercent, height.toFloat() / 2)
        path.lineTo(width / 2.toFloat() + xxSize, height.toFloat() / 2 + xxSize)
        path.lineTo(width / 2.toFloat(), height / 2.toFloat() + height / 2.toFloat() * floatPercent)
        path.lineTo(width / 2.toFloat() - xxSize, height.toFloat() / 2 + xxSize)
        path.lineTo(width / 2.toFloat() * (1 - floatPercent), height / 2.toFloat())
        path.lineTo(width / 2.toFloat() - xxSize, height.toFloat() / 2 - xxSize)
        path.close()

        canvas.drawPath(path, mPaint)
        // 隔一段时间重绘一次, 动画效果
//        handler.postDelayed(runnable, intervalTime.toLong())
    }


    //当前是否处于放大过程中
    var isIncreasing = false
    var percentValue = 100

    // 重绘线程
    private val runnable = Runnable {
        smoothChangeRadiusSize()
        invalidate()
    }


    /**
     * 先缩小，后放大，营造呼吸效果
     */
    private fun smoothChangeRadiusSize() {
        if (isIncreasing) {
            percentValue++
        } else {
            percentValue--
        }
        if (percentValue <= 10) {
            percentValue++
            isIncreasing = true
        } else if (percentValue >= 100) {
            percentValue--
            isIncreasing = false
        }
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}