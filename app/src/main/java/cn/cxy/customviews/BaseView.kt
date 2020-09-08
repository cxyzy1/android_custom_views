package cn.cxy.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

open class BaseView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private var defaultSize = 200
    protected var mWidth = defaultSize
    protected var mHeight = defaultSize

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = startMeasure(widthMeasureSpec)
        mHeight = startMeasure(heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
    }

    private fun startMeasure(msSpec: Int): Int {
        val mode = MeasureSpec.getMode(msSpec)
        val size = MeasureSpec.getSize(msSpec)
        return if (mode == MeasureSpec.EXACTLY) {
            size
        } else {
            dp2Px(context, defaultSize).toInt()
        }
    }

    protected fun dp2Px(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
    }
}