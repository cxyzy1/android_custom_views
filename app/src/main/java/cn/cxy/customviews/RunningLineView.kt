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

    var startPoint = Point(width, 0)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        path.reset()
        drawLineFromStartPoint(
            200,
            startPoint,
            width,
            height
        )
        handler.postDelayed(runnable, intervalTime.toLong())
    }

    private fun drawLineFromStartPoint(
        lineLength: Int,
        startPosition: Point,
        width: Int,
        height: Int
    ) {
        val currentX = startPosition.x
        val currentY = startPosition.y

        if (isOnTop(currentY)) {
            val offset = currentX
            if (offset >= lineLength) {
                drawLine(currentX, currentY + strokeWidth / 2, currentX - lineLength, currentY + strokeWidth / 2)
            } else {
                drawLine(currentX, currentY + strokeWidth / 2,0, currentY + strokeWidth / 2)
                drawLine(strokeWidth / 2, currentY, strokeWidth / 2, currentY + lineLength - offset)
            }
        } else if (isOnBottom(currentY, height)) {
            val offset = abs(width - currentX)
            if (offset >= lineLength) {
                drawLine(currentX, currentY - strokeWidth / 2, currentX + lineLength, currentY - strokeWidth / 2)
            } else {
                drawLine(currentX, currentY - strokeWidth / 2, width, currentY - strokeWidth / 2)
                drawLine(width - strokeWidth / 2, currentY, width - strokeWidth / 2, currentY - lineLength + offset)
            }
        } else if (isOnRight(currentX, width)) {
            val offset = abs(currentY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY - lineLength)
            } else {
                drawLine(currentX, currentY, currentX,0)
                drawLine(width - strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2 - lineLength + offset, strokeWidth / 2)
            }
        } else if (isOnLeft(currentX, width)) {
            val offset = abs(height - currentY)
            if (offset >= lineLength) {
                drawLine(currentX, currentY, currentX, currentY + lineLength)
            } else {
                drawLine(currentX, currentY, currentX, height - strokeWidth / 2)
                drawLine(0,height - strokeWidth / 2, lineLength - offset, height - strokeWidth / 2)
            }
        }
    }

    private fun isOnLeft(currentX: Int, width: Int) =
        currentX == strokeWidth / 2

    private fun isOnRight(currentX: Int, width: Int) =
        currentX == width - strokeWidth / 2

    private fun isOnBottom(currentY: Int, height: Int) =
        currentY == height - strokeWidth / 2

    private fun isOnTop(currentY: Int) = currentY == 0

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
                startPoint.x = width
                startPoint.y++
            }
        } else if (isOnRight(startPoint.x, width)) {
            if (startPoint.y <= height) {
                startPoint.y++
            } else {
                startPoint.y = height
                startPoint.x--
            }
        } else if (isOnBottom(startPoint.y, height)) {
            if (startPoint.x >= 0) {
                startPoint.x--
            } else {
                startPoint.x = 0
                startPoint.y--
            }
        } else if (isOnLeft(startPoint.x, width)) {
            if (startPoint.y >= 0) {
                startPoint.y--
            } else {
                startPoint.y = 0
                startPoint.x++
            }
        }
        invalidate()
    }
}