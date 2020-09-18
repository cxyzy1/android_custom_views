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

    var pointList = mutableListOf<Point>()
    var segmentStartIndex = 0
    var mStepSizeX = 0F
    var mStepSizeY = 0F
    var middlePointX = 0F
    var middlePointY = 0F

    init {
        pointList.add(Point(mLineWidth / 2, 0))
        pointList.add(Point(mLineWidth / 2, 1000))
        pointList.add(Point(800, 400))
        pointList.add(Point(900, 1600))
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

        if (pointList.size > 1) {
            mPath.reset()

            mPath.moveTo(pointList[0].x.toFloat(), pointList[0].y.toFloat())
            for (i in 1..segmentStartIndex) {
                mPath.lineTo(pointList[i].x.toFloat(), pointList[i].y.toFloat())
            }

            mPath.lineTo(middlePointX, middlePointY)

            canvas.drawPath(mPath, mPaint)
            if (mRunFlag) {
                handler.postDelayed(runnable, mIntervalTime.toLong())
            }
        }
    }

    // 重绘线程
    private val runnable = Runnable {
        changeMiddlePoint()
        if (segmentStartIndex >= pointList.size - 1) {
            mRunFlag = false
        } else {
            invalidate()
        }
    }

    fun start() {
        post {
            middlePointX = pointList[0].x.toFloat()
            middlePointY = pointList[0].y.toFloat()
            updateSegmentStepSize()
            changeMiddlePoint()

            mRunFlag = true
            invalidate()
        }
    }

    /**
     * 改变当前线段的中间点的位置
     */
    private fun changeMiddlePoint() {
        val mSegmentStartPoint = pointList[segmentStartIndex]
        val mSegmentEndPoint = pointList[segmentStartIndex + 1]
        middlePointX += mStepSizeX
        middlePointY += mStepSizeY
        if (abs(middlePointX - mSegmentStartPoint.x) >= abs(mSegmentEndPoint.x - mSegmentStartPoint.x)
            && abs(middlePointY - mSegmentStartPoint.y) >= abs(mSegmentEndPoint.y - mSegmentStartPoint.y)
        ) {
            middlePointX = mSegmentEndPoint.x.toFloat()
            middlePointY = mSegmentEndPoint.y.toFloat()
            segmentStartIndex++
            updateSegmentStepSize()
        }
    }

    private fun updateSegmentStepSize() {
        if (segmentStartIndex < pointList.size - 1) {
            val segmentStartPoint = pointList[segmentStartIndex]
            val segmentEndPoint = pointList[segmentStartIndex + 1]
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