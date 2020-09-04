package cn.cxy.snowfall.t4

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
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

    class Builder {
        var initSpeed: Int
        var bitmap: Bitmap

        constructor(bitmap: Bitmap) {
            initSpeed = defaultSpeed
            this.bitmap = bitmap
        }

        constructor(drawable: Drawable) {
            initSpeed = defaultSpeed
            bitmap = drawableToBitmap(drawable)
        }

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
        fun setSize(w: Int, h: Int): Builder {
            bitmap = changeBitmapSize(bitmap, w, h)
            return this
        }

        /**
         * 改变bitmap的大小
         * @param bitmap 目标bitmap
         * @param newW 目标宽度
         * @param newH 目标高度
         * @return
         */
        fun changeBitmapSize(bitmap: Bitmap, newW: Int, newH: Int): Bitmap {
            var bitmap = bitmap
            val oldW = bitmap.width
            val oldH = bitmap.height
            // 计算缩放比例
            val scaleWidth = newW.toFloat() / oldW
            val scaleHeight = newH.toFloat() / oldH
            // 取得想要缩放的matrix参数
            val matrix = Matrix()
            matrix.postScale(scaleWidth, scaleHeight)
            // 得到新的图片
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, oldW, oldH, matrix, true)
            return bitmap
        }

        companion object {
            /**
             * drawable图片资源转bitmap
             * @param drawable
             * @return
             */
            fun drawableToBitmap(drawable: Drawable): Bitmap {
                val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
                )
                val canvas = Canvas(bitmap)
                drawable.setBounds(
                    0,
                    0,
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight
                )
                drawable.draw(canvas)
                return bitmap
            }
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