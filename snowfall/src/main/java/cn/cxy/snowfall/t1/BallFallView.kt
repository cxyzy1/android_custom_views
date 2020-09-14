package cn.cxy.snowfall.t1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

/**
 * 圆点飘落
 */
class BallFallView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var viewWidth = 0
    private var viewHeight = 0
    private var mPaint = Paint()
    private var snowX = 100f
    private var snowY = 0
    private var radius = 25f
    private val defaultWidth = 600 //默认宽度
    private val defaultHeight = 1000 //默认高度
    private val intervalTime = 5 //重绘间隔时间

    init {
        mPaint.color = Color.WHITE
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureSize(defaultHeight, heightMeasureSpec)
        val width = measureSize(defaultWidth, widthMeasureSpec)
        setMeasuredDimension(width, height)
        viewWidth = width
        viewHeight = height
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = min(result, specSize)
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(snowX, snowY.toFloat(), radius, mPaint)
        handler.postDelayed(runnable, intervalTime.toLong()) //间隔一段时间再进行重绘
    }

    // 重绘线程
    private val runnable = Runnable {
        snowY += 15
        if (snowY > viewHeight) { //超出屏幕则重置雪球位置
            snowY = 0
        }
        invalidate()
    }
}