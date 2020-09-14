package cn.cxy.customviews.misc

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class CustomTextView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mTextViewString = "$1000,0000"
    private val mTextViewColor = Color.parseColor("#0000FF")
    private val mTextViewSize = 100
    private val mTextViewPaint: Paint = Paint()
    private val mTextViewBound: Rect

    init {
        mTextViewPaint.textSize = mTextViewSize.toFloat()
        mTextViewBound = Rect()
        mTextViewPaint.getTextBounds(mTextViewString, 0, mTextViewString.length, mTextViewBound)
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
        val widthMode = MeasureSpec.getMode(widthMeasureSpec) //得到布局文件中宽高设置的类型
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val myWidth = setWidth(widthMode, widthMeasureSpec)
        val myHeight = setHeight(heightMode, heightMeasureSpec)
        setMeasuredDimension(myWidth, myHeight)
    }

    private fun setWidth(widthMode: Int, widthMeasureSpec: Int): Int {
        return if (widthMode == MeasureSpec.EXACTLY) {
            widthMeasureSpec
        } else {
            mTextViewBound.width() + paddingLeft + paddingRight
        }
    }

    private fun setHeight(heightMode: Int, heightMeasureSpec: Int): Int {
        return if (heightMode == MeasureSpec.EXACTLY) {
            heightMeasureSpec
        } else {
            mTextViewBound.height() + paddingTop + paddingBottom
        }
    }
}