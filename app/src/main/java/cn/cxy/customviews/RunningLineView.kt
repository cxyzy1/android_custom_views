package cn.cxy.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import java.lang.Math.abs


/**
 * 移动的线段
 */
class RunningLineView(context: Context, attrs: AttributeSet? = null) : BaseView(context, attrs) {
    private lateinit var mCanvas: Canvas
    private val mPaint = Paint()
    private val mPath = Path()

    //重绘间隔时间
    private val mIntervalTime = 10

    //线段宽度
    private var mLineWidth = dp2Px(context, 4f).toInt()

    //线段长度
    private var mLineLength = dp2Px(context, 50f).toInt()

    //每次移动的距离
    private var mStepSize = 20

    //起始点
    private var mStartPosition = Point(0, mLineWidth / 2)

    //起始点X坐标所处位置百分比，取值范围：0到1
    private var mXBias = 0F

    //起始点Y坐标所处位置百分比，取值范围：0到1
    private var mYBias = 0F

    //是否开始绘制并定时刷新
    private var mRunFlag = false

    init {
        //设置实心
        mPaint.style = Paint.Style.STROKE
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = mLineWidth.toFloat()
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mCanvas = canvas
        mPath.reset()
        drawLineFromStartPoint(mStartPosition)
        if (mRunFlag) {

            handler.postDelayed(runnable, mIntervalTime.toLong())
        }
    }

    private fun drawLineFromStartPoint(startPosition: Point) {
        val currentX = startPosition.x
        val currentY = startPosition.y

        when {
            isOnTop(currentY) -> {
                drawOnTop(currentX, currentY)
            }
            isOnRight(currentX) -> {
                drawOnRight(currentY, currentX)
            }
            isOnBottom(currentY) -> {
                drawOnBottom(currentX, currentY)
            }
            isOnLeft(currentX) -> {
                drawOnLeft(currentY, currentX)
            }
        }
    }

    private fun drawOnLeft(currentY: Int, currentX: Int) {
        val offset = abs(height - currentY)
        if (offset >= mLineLength) {
            drawLine(currentX, currentY, currentX, currentY + mLineLength)
        } else {
            drawLine(currentX, currentY, currentX, height - mLineWidth / 2)
            drawLine(0, height - mLineWidth / 2, mLineLength - offset, height - mLineWidth / 2)
        }
    }

    private fun drawOnRight(currentY: Int, currentX: Int) {
        val offset = abs(currentY)
        if (offset >= mLineLength) {
            drawLine(currentX, currentY, currentX, currentY - mLineLength)
        } else {
            drawLine(currentX, currentY, currentX, 0)
            drawLine(width - mLineWidth / 2, mLineWidth / 2, width - mLineWidth / 2 - mLineLength + offset, mLineWidth / 2)
        }
    }

    private fun drawOnBottom(currentX: Int, currentY: Int) {
        val offset = abs(width - currentX)
        if (offset >= mLineLength) {
            drawLine(currentX, currentY, currentX + mLineLength, currentY)
        } else {
            drawLine(currentX, currentY, width, currentY)
            drawLine(width - mLineWidth / 2, currentY, width - mLineWidth / 2, currentY - mLineLength + offset)
        }
    }

    private fun drawOnTop(currentX: Int, currentY: Int) {
        val offset = currentX
        if (offset >= mLineLength) {
            drawLine(currentX, currentY, currentX - mLineLength, currentY)
        } else {
            drawLine(currentX, currentY, 0, currentY)
            drawLine(mLineWidth / 2, currentY, mLineWidth / 2, currentY + mLineLength - offset)
        }
    }

    private fun isOnTop(currentY: Int) = currentY <= mLineWidth / 2

    private fun isOnRight(currentX: Int) = currentX >= width - mLineWidth / 2

    private fun isOnBottom(currentY: Int) = currentY >= height - mLineWidth / 2

    private fun isOnLeft(currentX: Int) = currentX <= mLineWidth / 2

    private fun drawLine(fromX: Int, fromY: Int, toX: Int, toY: Int) {
        mPath.moveTo(fromX.toFloat(), fromY.toFloat())
        mPath.lineTo(toX.toFloat(), toY.toFloat())
        mCanvas.drawPath(mPath, mPaint)
    }

    // 重绘线程
    private val runnable = Runnable {
        if (isOnTop(mStartPosition.y)) {
            if (mStartPosition.x <= width) {
                mStartPosition.x += mStepSize
            } else {
                mStartPosition.x = width - mLineWidth / 2
                mStartPosition.y += mStepSize
            }
        } else if (isOnRight(mStartPosition.x)) {
            if (mStartPosition.y <= height) {
                mStartPosition.y += mStepSize
            } else {
                mStartPosition.y = height - mLineWidth / 2
                mStartPosition.x -= mStepSize
            }
        } else if (isOnBottom(mStartPosition.y)) {
            if (mStartPosition.x >= 0) {
                mStartPosition.x -= mStepSize
            } else {
                mStartPosition.x = mLineWidth / 2
                mStartPosition.y -= mStepSize
            }
        } else if (isOnLeft(mStartPosition.x)) {
            if (mStartPosition.y >= 0) {
                mStartPosition.y -= mStepSize
            } else {
                mStartPosition.y = mLineWidth / 2
                mStartPosition.x += mStepSize
            }
        }
        invalidate()
    }

    /**
     * 设置线段宽度
     */
    fun setLineWidth(@Dimension lineWidth: Float): RunningLineView {
        mLineWidth = dp2Px(context, lineWidth).toInt()
        mPaint.strokeWidth = mLineWidth.toFloat()
        return this
    }

    /**
     * 设置线段长度
     */
    fun setLineLength(_lineLength: Float): RunningLineView {
        mLineLength = dp2Px(context, _lineLength).toInt()
        return this
    }

    /**
     * 设置线段颜色
     */
    fun setLineColor(@ColorInt lineColor: Int): RunningLineView {
        mPaint.color = lineColor
        return this
    }

    /**
     * 设置线段颜色
     */
    fun setLineColorRes(@ColorRes lineColorRes: Int) = setLineColor(ContextCompat.getColor(context, lineColorRes))

    /**
     * 设置渐变色
     */
    fun setGradient(colors: IntArray): RunningLineView {
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
//        val position = floatArrayOf(0.1f, 0.6f, 0.8f)
        val mShader = LinearGradient(0f, 0f, mLineLength.toFloat(), mLineWidth.toFloat(), colors, null, Shader.TileMode.MIRROR)
        mPaint.shader = mShader
        return this
    }

    /**
     * 设置每次移动距离
     */
    fun setStepSize(stepSize: Int): RunningLineView {
        mStepSize = stepSize
        return this
    }

    /**
     * 设置起始点
     * 如果xBias为0或者1，则yBias可以为0到1之前任何值；
     * 如果yBias为0或者1，则xBias可以为0到1之前任何值。
     */
    fun setStartPosition(xBias: Float, yBias: Float): RunningLineView {
        checkParamsForStartPosition(xBias, yBias)
        mXBias = xBias
        mYBias = yBias
        return this
    }

    private fun updateStartPosition() {
        var xPos = mXBias * width
        var yPos = mYBias * height
        if (mYBias == 0f) {
            yPos = maxValue(mYBias * height, mLineWidth / 2.toFloat())
        } else if (mYBias == 1f) {
            yPos = minValue(mYBias * height, height - mLineWidth / 2.toFloat())
        } else if (mXBias == 0f) {
            xPos = maxValue(mXBias * width, mLineWidth / 2.toFloat())
        } else if (mXBias == 1f) {
            xPos = minValue(mXBias * width, width - mLineWidth / 2.toFloat())
        }

        mStartPosition = Point(xPos.toInt(), yPos.toInt())
    }

    fun start() {
        post {
            //updateStartPosition函数需要使用到width和height，需要等视图完成测量后才能获得,所以整体放到post中调用
            updateStartPosition()
            mRunFlag = true
            invalidate()
        }
    }

    fun stop() {
        mRunFlag = false
    }

    private fun checkParamsForStartPosition(xBias: Float, yBias: Float) {
        if (xBias == 0f || xBias == 1f) {
            if (yBias < 0f || yBias > 1f) {
                throw Exception("yBias必须在0和1之间")
            }
        } else if (xBias > 0f && xBias < 1f) {
            if (yBias != 0f && yBias != 1f) {
                throw Exception("xBias处于0到1之间时，yBias必须为0或1")
            }
        } else {
            throw Exception("xBias必须在0和1之间")
        }
    }


}