package cn.cxy.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.*

class CustomTextViewWithClick(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private var mTextViewString = "$1000,0000"
    private val mTextViewColor = Color.parseColor("#0000FF")
    private val mTextViewSize = 100
    private val mTextViewPaint: Paint = Paint()
    private val mTextViewBound: Rect
    private var mWidthMeasureSpec: Int = 0
    private var mHeightMeasureSpec: Int = 0

    init {
        mTextViewPaint.textSize = mTextViewSize.toFloat()
        mTextViewBound = Rect()
        mTextViewPaint.getTextBounds(mTextViewString, 0, mTextViewString.length, mTextViewBound)
        setOnClickListener { changeText() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

//        mTextViewPaint.color = Color.WHITE
//        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mTextViewPaint) //画布大小,onMeasure中测量大小

        mTextViewPaint.color = mTextViewColor //重置画笔颜色
        val fontMetrics = mTextViewPaint.fontMetricsInt
        val baseline = (measuredHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
        canvas.drawText(
            mTextViewString,
            measuredWidth / 2 - mTextViewBound.width() / 2.toFloat(),
            baseline.toFloat(),
            mTextViewPaint
        )
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidthMeasureSpec = widthMeasureSpec
        mHeightMeasureSpec = heightMeasureSpec
        setMeasuredDimension(getWidth(mWidthMeasureSpec), getHeight(mHeightMeasureSpec))
    }

    private fun getWidth(widthMeasureSpec: Int): Int {
        val widthMode = MeasureSpec.getMode(mWidthMeasureSpec)
        return if (widthMode == MeasureSpec.EXACTLY) {
            widthMeasureSpec
        } else {
            mTextViewBound.width() + paddingLeft + paddingRight
        }
    }

    private fun getHeight(heightMeasureSpec: Int): Int {
        val heightMode = MeasureSpec.getMode(mHeightMeasureSpec)
        return if (heightMode == MeasureSpec.EXACTLY) {
            heightMeasureSpec
        } else {
            mTextViewBound.height() + paddingTop + paddingBottom
        }
    }

    private fun changeText() {
        val random = Random()
        mTextViewString = "$" + random.nextLong()
        postInvalidate()
    }
}