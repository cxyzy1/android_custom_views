package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 扇形
 */
class SectorView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
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
        drawSector(canvas)
    }

    /**
     * 画扇形
     */
    private fun drawSector(canvas: Canvas, isFilled: Boolean = true) {
        setPaintStyle(isFilled)
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawArc(rectF, 30f, 120f, true, paint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        paint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}