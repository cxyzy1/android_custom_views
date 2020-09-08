package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 圆形
 */
class CircleView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
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
        drawCircle(canvas)
    }

    /**
     * 画圆形
     */
    private fun drawCircle(canvas: Canvas, isFilled: Boolean = true) {
        setPaintStyle(isFilled)
        canvas.drawCircle(
            width.toFloat() / 2,
            height.toFloat() / 2,
            min(width, height).toFloat() / 2,
            paint
        )
    }

    private fun setPaintStyle(isFilled: Boolean) {
        paint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }

    fun min(value1: Int, value2: Int): Int {
        return if (value1 <= value2) {
            value1
        } else {
            value2
        }
    }
}