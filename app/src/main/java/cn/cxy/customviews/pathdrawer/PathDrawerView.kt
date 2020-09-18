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
    private lateinit var mCanvas: Canvas
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

    var list = mutableListOf<Point>()
    var currentIndex = 0

    init {
        list.add(Point(mLineWidth / 2, 0))
        list.add(Point(mLineWidth / 2, 1000))
        list.add(Point(800, 400))
        list.add(Point(900, 1600))
        //设置实心
        mPaint.style = Paint.Style.STROKE
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = mLineWidth.toFloat()
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    var segmentStartIndex = 0
    var offset = 0F
    var mStepSizeX = 0F
    var mStepSizeY = 0F
    var middlePointX = 0F
    var middlePointY = 0F
    lateinit var mSegmentStartPoint: Point
    lateinit var mSegmentEndPoint: Point
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mCanvas = canvas
        mPath.reset()

        mPath.moveTo(list[0].x.toFloat(), list[0].y.toFloat())
//        currentIndex = list.size - 1
        for (i in 1..segmentStartIndex) {
            mPath.lineTo(list[i].x.toFloat(), list[i].y.toFloat())
        }

        mPath.lineTo(middlePointX, middlePointY)

        mCanvas.drawPath(mPath, mPaint)
        if (mRunFlag) {
            handler.postDelayed(runnable, mIntervalTime.toLong())
        }
    }

    // 重绘线程
    private val runnable = Runnable {
        mSegmentStartPoint = list[segmentStartIndex]
        mSegmentEndPoint = list[segmentStartIndex + 1]
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

        if (segmentStartIndex >= list.size - 1) {
            mRunFlag = false
        } else {
            invalidate()
        }
    }

    fun start() {
        post {
            mSegmentStartPoint = list[segmentStartIndex]
            mSegmentEndPoint = list[segmentStartIndex + 1]
            middlePointX = mSegmentStartPoint.x.toFloat()
            middlePointY = mSegmentStartPoint.y.toFloat()
            updateSegmentStepSize()
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

            mRunFlag = true
            invalidate()
        }
    }


    private fun updateSegmentStepSize() {
        if (segmentStartIndex < list.size - 1) {
            val segmentStartPoint = list[segmentStartIndex]
            val segmentEndPoint = list[segmentStartIndex + 1]
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