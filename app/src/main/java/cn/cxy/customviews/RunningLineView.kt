package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import java.lang.Math.abs

/**
 * 移动的线段
 */
class RunningLineView(context: Context, attrs: AttributeSet? = null) : BaseView(context, attrs) {
    private val mPaint = Paint()
    private val path = Path()
    private val strokeWidth = dp2Px(context, 4f).toInt()
    private val intervalTime = 5 //重绘间隔时间
    private lateinit var mCanvas: Canvas

    init {
        //设置实心
        mPaint.style = Paint.Style.STROKE
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = strokeWidth.toFloat()
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        drawLine()
    }

    lateinit var startPoint: Point
    private fun drawLine() {
        path.reset()
        drawLineFromStartPoint(
            200,
            startPoint,
            Point(0, 0),
            width,
            height
        )
//        path.moveTo(0f, 0f)
//        path.lineTo(width.toFloat() / 3, 0f)
//        path.close()
//        canvas.drawPath(path, mPaint)
//        handler.postDelayed(runnable, intervalTime.toLong())
    }

    private fun drawLineFromStartPoint(
        lineLength: Int,
        startPosition: Point,
        basePosition: Point,
        width: Int,
        height: Int
    ) {
        val currentX = startPosition.x
        val currentY = startPosition.y
        val baseX = basePosition.x
        val baseY = basePosition.y

        if (isOnTop(currentY, baseY)) {
            val offset = abs(currentX - baseX)
            if (offset >= lineLength) {
                drawLine(currentX, currentY + strokeWidth / 2, currentX - lineLength, currentY + strokeWidth / 2)
            } else {
                drawLine(currentX, currentY + strokeWidth / 2, baseX, currentY + strokeWidth / 2)
                drawLine(baseX + strokeWidth / 2, currentY, baseX + strokeWidth / 2, currentY + lineLength - offset)
            }
        } else if (isOnBottom(currentY, baseY, height)) {
            val offset = abs(baseX + width - currentX)
            if (offset >= lineLength) {
                drawLine(currentX, currentY - strokeWidth / 2, currentX + lineLength, currentY - strokeWidth / 2)
            } else {
                drawLine(currentX, currentY - strokeWidth / 2, baseX + width, currentY - strokeWidth / 2)
                drawLine(baseX + width - strokeWidth / 2, currentY, baseX + width - strokeWidth / 2, currentY - lineLength + offset)
            }
        } else if (isOnRight(currentX, baseX, width)) {
            val offset = abs(currentY - baseY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY - lineLength)
            } else {
                drawLine(currentX, currentY, currentX, baseY)
                drawLine(baseX + width - strokeWidth / 2, baseY + strokeWidth / 2, baseX + width - strokeWidth / 2 - lineLength + offset, baseY + strokeWidth / 2)
            }
        } else if (isOnLeft(currentX, baseX, width)) {
            val offset = abs(height + baseY - currentY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY + lineLength)
            } else {
                drawLine(currentX, currentY, currentX, baseY + height - strokeWidth / 2)
                drawLine(baseX, baseY + height - strokeWidth / 2, baseX + lineLength - offset, baseY + height - strokeWidth / 2)
            }
        }

    }

    private fun isOnLeft(currentX: Int, baseX: Int, width: Int) =
        currentX == baseX + strokeWidth / 2

    private fun isOnRight(currentX: Int, baseX: Int, width: Int) =
        currentX == baseX + width - strokeWidth / 2

    private fun isOnBottom(currentY: Int, baseY: Int, height: Int) =
        currentY == baseY + height - strokeWidth / 2

    private fun isOnTop(currentY: Int, baseY: Int) = currentY == baseY

    private fun drawLine(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        path.moveTo(fromX.toFloat(), fromY.toFloat())
        path.lineTo(toX.toFloat(), toY.toFloat())
        mCanvas.drawPath(path, mPaint)
    }

    // 重绘线程
    private val runnable = Runnable {

        invalidate()
    }
}