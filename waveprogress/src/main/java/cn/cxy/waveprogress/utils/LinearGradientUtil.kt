package cn.cxy.waveprogress.utils

import android.graphics.Color

class LinearGradientUtil(private var mStartColor: Int, private var mEndColor: Int) {
    fun setStartColor(startColor: Int) {
        mStartColor = startColor
    }

    fun setEndColor(endColor: Int) {
        mEndColor = endColor
    }

    //获取某一个百分比间的颜色,radio取值[0,1]
    fun getColor(radio: Float): Int {
        val redStart = Color.red(mStartColor)
        val blueStart = Color.blue(mStartColor)
        val greenStart = Color.green(mStartColor)
        val redEnd = Color.red(mEndColor)
        val blueEnd = Color.blue(mEndColor)
        val greenEnd = Color.green(mEndColor)
        val red = (redStart + ((redEnd - redStart) * radio + 0.5)).toInt()
        val greed = (greenStart + ((greenEnd - greenStart) * radio + 0.5)).toInt()
        val blue = (blueStart + ((blueEnd - blueStart) * radio + 0.5)).toInt()
        return Color.argb(255, red, greed, blue)
    }

}