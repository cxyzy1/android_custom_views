package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 方形
 */
class RectView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
    private val paint = Paint()

    init {
        //设置实心
        paint.style = Paint.Style.FILL
        //设置颜色
        paint.color = Color.BLUE
        // 设置画笔的锯齿效果
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRect(canvas)
    }

    /**
     * 画矩形
     */
    private fun drawRect(canvas: Canvas, isFilled: Boolean = true) {
        setPaintStyle(isFilled)
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        paint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}