package cn.cxy.customviews.misc

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 扇形
 */
class SectorView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
    private val mPaint = Paint()

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
        drawSector(canvas)
    }

    /**
     * 画扇形
     */
    private fun drawSector(canvas: Canvas, isFilled: Boolean = true) {
        setPaintStyle(isFilled)
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        canvas.drawArc(rectF, 30f, 120f, true, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}