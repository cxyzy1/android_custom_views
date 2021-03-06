package cn.cxy.waveprogress.utils

import android.content.Context

object DpOrPxUtils {
    fun dp2Px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}