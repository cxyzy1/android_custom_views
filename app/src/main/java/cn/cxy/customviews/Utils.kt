package cn.cxy.customviews

import android.content.Context
import android.util.TypedValue

fun dp2Px(context: Context, dp: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    )
}

fun minValue(value1: Int, value2: Int): Int {
    return if (value1 <= value2)
        value1
    else
        value2
}

fun minValue(value1: Float, value2: Float): Float {
    return if (value1 <= value2)
        value1
    else
        value2
}

fun maxValue(value1: Float, value2: Float): Float {
    return if (value1 >= value2)
        value1
    else
        value2
}