package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

/**
 * 移动的线段
 */
class RunningLineView(context: Context, attrs: AttributeSet?=null) : BaseView(context, attrs) {
    private val mPaint = Paint()
    private val path = Path()
    private val strokeWidth = dp2Px(context, 4f)

    init {
        //设置实心
        mPaint.style = Paint.Style.STROKE
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = strokeWidth
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawLine(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        path.reset()
        path.moveTo(0f, 0f)
        path.lineTo(width.toFloat()/2, 0f)
        path.close()
        canvas.drawPath(path, mPaint)
    }
}