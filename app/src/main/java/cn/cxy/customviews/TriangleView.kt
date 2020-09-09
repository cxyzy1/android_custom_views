package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 三角形
 */
class TriangleView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
    private val mPaint = Paint()
    private val path = Path()

    init {
        //设置实心
        mPaint.style = Paint.Style.FILL
        //设置颜色
        mPaint.color = Color.BLUE
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTriangle(canvas)
    }

    /**
     * 画三角形
     */
    private fun drawTriangle(canvas: Canvas, isFilled: Boolean = true) {
        setPaintStyle(isFilled)
        path.moveTo(width.toFloat() / 2, 0f)
        path.lineTo(width.toFloat(), height.toFloat())
        path.lineTo(0f, height.toFloat())
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}