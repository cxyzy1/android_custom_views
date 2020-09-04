package cn.cxy.snowfall.t3

import android.graphics.Bitmap
import android.graphics.Canvas
import java.util.*

class FallObject {
    private var initX = 0
    private var initY = 0
    private var random: Random? = null
    private var parentWidth = 0 //父容器宽度
    private var parentHeight = 0 //父容器高度
    private var objectWidth = 0f //下落物体宽度
    private var objectHeight = 0f //下落物体高度
    private var initSpeed = 0 //初始下降速度
    private var presentX = 0f //当前位置X坐标
    private var presentY = 0f //当前位置Y坐标
    private var presentSpeed = 0f //当前下降速度
    private var bitmap: Bitmap
    lateinit var builder: Builder

    constructor(builder: Builder, parentWidth: Int, parentHeight: Int) {
        random = Random()
        this.parentWidth = parentWidth
        this.parentHeight = parentHeight
        initX = random!!.nextInt(parentWidth) //随机物体的X坐标
        initY = random!!.nextInt(parentHeight) - parentHeight //随机物体的Y坐标，并让物体一开始从屏幕顶部下落
        presentX = initX.toFloat()
        presentY = initY.toFloat()
        initSpeed = builder.initSpeed
        presentSpeed = initSpeed.toFloat()
        bitmap = builder.bitmap
        objectWidth = bitmap.width.toFloat()
        objectHeight = bitmap.height.toFloat()
    }

    private constructor(builder: Builder) {
        this.builder = builder
        initSpeed = builder.initSpeed
        bitmap = builder.bitmap
    }

    class Builder(bitmap: Bitmap) {
        var initSpeed: Int
        val bitmap: Bitmap

        /**
         * 设置物体的初始下落速度
         * @param speed
         * @return
         */
        fun setSpeed(speed: Int): Builder {
            initSpeed = speed
            return this
        }

        fun build(): FallObject {
            return FallObject(this)
        }

        init {
            initSpeed = defaultSpeed
            this.bitmap = bitmap
        }
    }

    /**
     * 绘制物体对象
     * @param canvas
     */
    fun drawObject(canvas: Canvas) {
        moveObject()
        canvas.drawBitmap(bitmap, presentX, presentY, null)
    }

    /**
     * 移动物体对象
     */
    private fun moveObject() {
        moveY()
        if (presentY > parentHeight) {
            reset()
        }
    }

    /**
     * Y轴上的移动逻辑
     */
    private fun moveY() {
        presentY += presentSpeed
    }

    /**
     * 重置object位置
     */
    private fun reset() {
        presentY = -objectHeight
        presentSpeed = initSpeed.toFloat()
    }

    companion object {
        private const val defaultSpeed = 10 //默认下降速度
    }
}