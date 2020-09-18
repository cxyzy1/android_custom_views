package cn.cxy.customviews.pathdrawer

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import cn.cxy.customviews.misc.BaseView
import cn.cxy.customviews.util.dp2Px
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * 动态画各种线
 */
class PathDrawerView(context: Context, attrs: AttributeSet? = null) : BaseView(context, attrs) {
    private val mPaint = Paint()
    private val mPath = Path()

    //重绘间隔时间
    private val mIntervalTime = 10

    //线段宽度
    private var mLineWidth = dp2Px(context, 4f).toInt()

    //每次移动的距离
    private var mStepSize = 10

    //是否开始绘制并定时刷新
    private var mRunFlag = false

    private var mPointList = mutableListOf<Point>()
    private var mSegmentStartIndex = 0
    private var mStepSizeX = 0F
    private var mStepSizeY = 0F
    private var mMiddlePointX = 0F
    private var mMiddlePointY = 0F

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

        if (mPointList.size > 1) {
            mPath.reset()

            mPath.moveTo(mPointList[0].x.toFloat(), mPointList[0].y.toFloat())
            for (i in 1..mSegmentStartIndex) {
                mPath.lineTo(mPointList[i].x.toFloat(), mPointList[i].y.toFloat())
            }

            mPath.lineTo(mMiddlePointX, mMiddlePointY)

            canvas.drawPath(mPath, mPaint)
            if (mRunFlag) {
                handler.postDelayed(runnable, mIntervalTime.toLong())
            }
        }
    }

    // 重绘线程
    private val runnable = Runnable {
        changeMiddlePoint()
        if (mSegmentStartIndex >= mPointList.size - 1) {
            mRunFlag = false
        } else {
            invalidate()
        }
    }

    fun start(pointList: List<PointF>) {
        if (pointList.size > 1) {
            post {
                pointList.forEach {
                    mPointList.add(Point((it.x * width).toInt(), (it.y * height).toInt()))
                }
                mMiddlePointX = mPointList[0].x.toFloat()
                mMiddlePointY = mPointList[0].y.toFloat()
                updateSegmentStepSize()
                changeMiddlePoint()

                mRunFlag = true
                invalidate()
            }
        }
    }

    /**
     * 改变当前线段的中间点的位置
     */
    private fun changeMiddlePoint() {
        val mSegmentStartPoint = mPointList[mSegmentStartIndex]
        val mSegmentEndPoint = mPointList[mSegmentStartIndex + 1]
        mMiddlePointX += mStepSizeX
        mMiddlePointY += mStepSizeY
        if (abs(mMiddlePointX - mSegmentStartPoint.x) >= abs(mSegmentEndPoint.x - mSegmentStartPoint.x)
            && abs(mMiddlePointY - mSegmentStartPoint.y) >= abs(mSegmentEndPoint.y - mSegmentStartPoint.y)
        ) {
            mMiddlePointX = mSegmentEndPoint.x.toFloat()
            mMiddlePointY = mSegmentEndPoint.y.toFloat()
            mSegmentStartIndex++
            updateSegmentStepSize()

//            changeColor()
        }
    }

    private fun changeColor() {
        mPaint.color = listOf(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN).random()
    }

    private fun updateSegmentStepSize() {
        if (mSegmentStartIndex < mPointList.size - 1) {
            val segmentStartPoint = mPointList[mSegmentStartIndex]
            val segmentEndPoint = mPointList[mSegmentStartIndex + 1]
            val pointsDistance = getDistance(segmentEndPoint, segmentStartPoint)
            val ratio = mStepSize / pointsDistance
            mStepSizeX = (ratio * (segmentEndPoint.x - segmentStartPoint.x)).toFloat()
            mStepSizeY = (ratio * (segmentEndPoint.y - segmentStartPoint.y)).toFloat()
        }
    }

    private fun getDistance(segmentEndPoint: Point, segmentStartPoint: Point): Double {
        return sqrt((segmentEndPoint.x - segmentStartPoint.x).toDouble().pow(2.toDouble()) + (segmentEndPoint.y - segmentStartPoint.y).toDouble().pow(2.toDouble()))
    }

    /**
     * 设置线段宽度
     */
    fun setLineWidth(@Dimension lineWidth: Float): PathDrawerView {
        mLineWidth = dp2Px(context, lineWidth).toInt()
        mPaint.strokeWidth = mLineWidth.toFloat()
        return this
    }

    /**
     * 设置线段颜色
     */
    fun setLineColor(@ColorInt lineColor: Int): PathDrawerView {
        mPaint.color = lineColor
        return this
    }

    /**
     * 设置线段颜色
     */
    fun setLineColorRes(@ColorRes lineColorRes: Int) = setLineColor(ContextCompat.getColor(context, lineColorRes))

    /**
     * 设置每次移动距离
     */
    fun setStepSize(stepSize: Int): PathDrawerView {
        mStepSize = stepSize
        return this
    }

    fun stop() {
        mRunFlag = false
    }

}