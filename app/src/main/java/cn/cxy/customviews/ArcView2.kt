package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 圆弧
 */
class ArcView2(context: Context, attrs: AttributeSet?=null) : View(context, attrs) {
    private val paint = Paint()
    private val strokeWidth = dp2Px(context, 4f)
    private val path = Path()
    init {
        //设置实心
        paint.style = Paint.Style.FILL
        //设置颜色
        paint.color = Color.BLUE
        //设置线宽
        paint.strokeWidth = strokeWidth
        // 设置画笔的锯齿效果
        paint.isAntiAlias = true
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
        path.moveTo(0f,0f)
        path.cubicTo(0f,0f,width.toFloat()/2,height.toFloat(),width.toFloat(),0f)
        canvas.drawPath(path, paint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        paint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}