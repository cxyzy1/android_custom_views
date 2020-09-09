package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 * 星形,带动画
 */
class StarViewWithAnimation2(context: Context, attrs: AttributeSet? = null) :
    BaseView(context, attrs) {
    private val mPaint = Paint()
    private val intervalTime = 50 //重绘间隔时间
    private val path = Path()
    private var centerRectSize = dp2Px(context, 10F)//中间矩形大小
    private var isIncreasing = false //当前是否处于放大过程中
    private var percentValue = 100 //缩放比例

    init {
        //设置实心
        mPaint.style = Paint.Style.FILL
        //设置颜色
        mPaint.color = Color.BLUE
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        centerRectSize = mWidth / 20.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStar(canvas)
    }


    private fun drawStar(canvas: Canvas) {
        var floatPercent = percentValue / 100.toFloat()
        centerRectSize = mWidth*floatPercent / 20.toFloat()
        path.reset()

        //设置模糊
        val blurRadius = width* floatPercent / 20.toFloat()
        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

        //绘制图形
        path.moveTo(width / 2.toFloat(), height / 2.toFloat() * (1 - floatPercent))
        path.lineTo(width / 2.toFloat() + centerRectSize, height.toFloat() / 2 - centerRectSize)
        path.lineTo(width / 2.toFloat() + width / 2.toFloat() * floatPercent, height.toFloat() / 2)
        path.lineTo(width / 2.toFloat() + centerRectSize, height.toFloat() / 2 + centerRectSize)
        path.lineTo(width / 2.toFloat(), height / 2.toFloat() + height / 2.toFloat() * floatPercent)
        path.lineTo(width / 2.toFloat() - centerRectSize, height.toFloat() / 2 + centerRectSize)
        path.lineTo(width / 2.toFloat() * (1 - floatPercent), height / 2.toFloat())
        path.lineTo(width / 2.toFloat() - centerRectSize, height.toFloat() / 2 - centerRectSize)
        path.close()

        canvas.drawPath(path, mPaint)
        // 隔一段时间重绘一次, 动画效果
        handler.postDelayed(runnable, intervalTime.toLong())
    }


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
        if (percentValue <= 0) {
            percentValue++
            isIncreasing = true
        } else if (percentValue >= 100) {
            percentValue--
            isIncreasing = false
        }
    }
}