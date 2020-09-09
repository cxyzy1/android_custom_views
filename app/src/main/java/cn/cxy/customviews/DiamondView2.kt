package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 菱形
 */
class DiamondView2(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mPaint = Paint()
    private val strokeWidth = dp2Px(context, 4f)
    private val path = Path()

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
        setPaintStyle(true)
        val blurRadius = width/10.toFloat()
        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

        path.moveTo(width/ 2.toFloat(), height/4.toFloat())
        path.lineTo(width*3/4.toFloat(), height.toFloat() / 2)
        path.lineTo(width/ 2.toFloat(), height*3/4.toFloat())
        path.lineTo(width/ 4.toFloat(), height/2.toFloat())
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }
}