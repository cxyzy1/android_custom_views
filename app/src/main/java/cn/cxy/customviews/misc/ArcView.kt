package cn.cxy.customviews.misc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import cn.cxy.customviews.util.dp2Px

/**
 * 圆弧
 */
class ArcView(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
    private val mPaint = Paint()
    val strokeWidth = dp2Px(context, 4f)

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
        drawArc(canvas)
    }

    /**
     * 画圆弧
     */
    private fun drawArc(canvas: Canvas) {
        setPaintStyle(false)
        val rectF = RectF(0f, 0f, width.toFloat() - strokeWidth, height.toFloat() - strokeWidth)
        canvas.drawArc(rectF, 30f, 120f, false, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}