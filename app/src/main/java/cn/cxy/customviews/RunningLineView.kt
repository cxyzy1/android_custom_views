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
    private val intervalTime = 2 //重绘间隔时间
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

    var startPoint = Point(0, strokeWidth / 2)
    var lineLength = 200
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        path.reset()
        drawLineFromStartPoint(startPoint)
        handler.postDelayed(runnable, intervalTime.toLong())
    }

    private fun drawLineFromStartPoint(startPosition: Point) {
        val currentX = startPosition.x
        val currentY = startPosition.y

        if (isOnTop(currentY)) {
            val offset = currentX
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX - lineLength, currentY)
            } else {
                drawLine(currentX, currentY, 0, currentY)
                drawLine(strokeWidth / 2, currentY, strokeWidth / 2, currentY + lineLength - offset)
            }
        } else if (isOnBottom(currentY)) {
            val offset = abs(width - currentX)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX + lineLength, currentY)
            } else {
                drawLine(currentX, currentY, width, currentY)
                drawLine(width - strokeWidth / 2, currentY, width - strokeWidth / 2, currentY - lineLength + offset)
            }
        } else if (isOnRight(currentX)) {
            val offset = abs(currentY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY - lineLength)
            } else {
                drawLine(currentX, currentY, currentX, 0)
                drawLine(width - strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2 - lineLength + offset, strokeWidth / 2)
            }
        } else if (isOnLeft(currentX)) {
            val offset = abs(height - currentY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY + lineLength)
            } else {
                drawLine(currentX, currentY, currentX, height - strokeWidth / 2)
                drawLine(0, height - strokeWidth / 2, lineLength - offset, height - strokeWidth / 2)
            }
        }
    }

    private fun isOnTop(currentY: Int) = currentY == strokeWidth / 2

    private fun isOnRight(currentX: Int) = currentX == width - strokeWidth / 2

    private fun isOnBottom(currentY: Int) = currentY == height - strokeWidth / 2

    private fun isOnLeft(currentX: Int) = currentX == strokeWidth / 2

    private fun drawLine(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        path.moveTo(fromX.toFloat(), fromY.toFloat())
        path.lineTo(toX.toFloat(), toY.toFloat())
        mCanvas.drawPath(path, mPaint)
    }

    // 重绘线程
    private val runnable = Runnable {
        if (isOnTop(startPoint.y)) {
            if (startPoint.x <= width) {
                startPoint.x++
            } else {
                startPoint.x = width - strokeWidth / 2
                startPoint.y++
            }
        } else if (isOnRight(startPoint.x)) {
            if (startPoint.y <= height) {
                startPoint.y++
            } else {
                startPoint.y = height - strokeWidth / 2
                startPoint.x--
            }
        } else if (isOnBottom(startPoint.y)) {
            if (startPoint.x >= 0) {
                startPoint.x--
            } else {
                startPoint.x = strokeWidth / 2
                startPoint.y--
            }
        } else if (isOnLeft(startPoint.x)) {
            if (startPoint.y >= 0) {
                startPoint.y--
            } else {
                startPoint.y = strokeWidth / 2
                startPoint.x++
            }
        }
        invalidate()
    }
}