package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 星形
 */
class StarView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mPaint = Paint()
    private val strokeWidth = dp2Px(context, 4f)
    private val path = Path()
    private val centerRectSize = dp2Px(context, 1f)

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

        path.moveTo(width / 2.toFloat(), 0f)
        path.lineTo(width / 2.toFloat() + centerRectSize, height.toFloat() / 2 - centerRectSize)
        path.lineTo(width.toFloat(), height.toFloat() / 2)
        path.lineTo(width / 2.toFloat() + centerRectSize, height.toFloat() / 2 + centerRectSize)
        path.lineTo(width / 2.toFloat(), height.toFloat())
        path.lineTo(width / 2.toFloat() - centerRectSize, height.toFloat() / 2 + centerRectSize)
        path.lineTo(0f, height / 2.toFloat())
        path.lineTo(width / 2.toFloat() - centerRectSize, height.toFloat() / 2 - centerRectSize)
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}