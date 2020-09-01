package cn.cxy.customviews.fall.t6

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import java.util.*
import kotlin.math.sin

class FallObject {
    private var initX = 0
    private var initY = 0
    private var random: Random = Random()
    private var mParentWidth = 0 //父容器宽度
    private var mParentHeight = 0 //父容器高度
    private var objectWidth = 0f //下落物体宽度
    private var objectHeight = 0f //下落物体高度
    private var initSpeed = 0 //初始下降速度
    var initWindLevel = 0 //初始风力等级
    private var presentX = 0f //当前位置X坐标
    private var presentY = 0f //当前位置Y坐标
    private var presentSpeed = 0f //当前下降速度
    private var bitmap: Bitmap
    var mBuilder: Builder
    private var isSpeedRandom = false //物体初始下降速度比例是否随机
    private var isSizeRandom = false //物体初始大小比例是否随机
    private var angle = 0f //物体下落角度
    private var isWindRandom = false //物体初始风向和风力大小比例是否随机
    private var isWindChange = false //物体下落过程中风向和风力是否产生随机变化
    private val defaultWindLevel = 0 //默认风力等级
    private val defaultWindSpeed = 10 //默认单位风速
    private val HALF_PI = Math.PI.toFloat() / 2 //π/2


    constructor(builder: Builder, parentWidth: Int, parentHeight: Int) {
        mBuilder = builder
        mParentWidth = parentWidth
        mParentHeight = parentHeight
        isSpeedRandom = builder.isSpeedRandom
        isSizeRandom = builder.isSizeRandom
        isWindRandom = builder.isWindRandom
        isWindChange = builder.isWindChange
        initX = random.nextInt(parentWidth) //随机物体的X坐标
        initY = random.nextInt(parentHeight) - parentHeight //随机物体的Y坐标，并让物体一开始从屏幕顶部下落
        presentX = initX.toFloat()
        presentY = initY.toFloat()
        initSpeed = builder.initSpeed
        presentSpeed = initSpeed.toFloat()
        bitmap = builder.bitmap
        objectWidth = bitmap.width.toFloat()
        objectHeight = bitmap.height.toFloat()
        randomSpeed()
        randomSize()
        randomWind()
    }

    private constructor(builder: Builder) {
        this.mBuilder = builder
        initSpeed = builder.initSpeed
        bitmap = builder.bitmap
        isSpeedRandom = builder.isSpeedRandom
        isSizeRandom = builder.isSizeRandom
        isWindRandom = builder.isWindRandom
        isWindChange = builder.isWindChange
        initWindLevel = builder.initWindLevel
    }

    /**
     * 随机物体初始下落速度
     */
    private fun randomSpeed() {
        presentSpeed = if (isSpeedRandom) {
            ((random.nextInt(3) + 1) * 0.1 + 1).toFloat() * initSpeed //这些随机数大家可以按自己的需要进行调整
        } else {
            initSpeed.toFloat()
        }
    }

    /**
     * 随机物体初始大小比例
     */
    private fun randomSize() {
        bitmap = if (isSizeRandom) {
            val r: Float = (random.nextInt(10) + 1) * 0.1f
            val rW: Float = r * mBuilder.bitmap.width
            val rH: Float = r * mBuilder.bitmap.height
            changeBitmapSize(mBuilder.bitmap, rW.toInt(), rH.toInt())
        } else {
            mBuilder.bitmap
        }
        objectWidth = bitmap.width.toFloat()
        objectHeight = bitmap.height.toFloat()
    }


    class Builder {
        var initSpeed: Int
        var bitmap: Bitmap
        var initWindLevel = 0
        internal var isSpeedRandom = false
        internal var isSizeRandom = false
        internal var isWindRandom = false
        internal var isWindChange = false

        constructor(bitmap: Bitmap) {
            initSpeed = defaultSpeed
            this.bitmap = bitmap
            this.isSpeedRandom = false
            this.isSizeRandom = false
            this.isWindRandom = false
            this.isWindChange = false
        }

        constructor(drawable: Drawable) {
            initSpeed = defaultSpeed
            bitmap = drawableToBitmap(drawable)
            this.isSpeedRandom = false
            this.isSizeRandom = false
            this.isWindRandom = false
            this.isWindChange = false
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

        /**
         * 设置物体的初始下落速度
         * @param speed
         * @param isRandomSpeed 物体初始下降速度比例是否随机
         * @return
         */
        fun setSpeed(speed: Int, isRandomSpeed: Boolean): Builder {
            initSpeed = speed
            isSpeedRandom = isRandomSpeed
            return this
        }


        /**
         * 设置风力等级、方向以及随机因素
         * @param level 风力等级（绝对值为 5 时效果会比较好），为正时风从左向右吹（物体向X轴正方向偏移），为负时则相反
         * @param isWindRandom 物体初始风向和风力大小比例是否随机
         * @param isWindChange 在物体下落过程中风的风向和风力是否会产生随机变化
         * @return
         */
        fun setWind(level: Int, isWindRandom: Boolean, isWindChange: Boolean): Builder {
            this.initWindLevel = level
            this.isWindRandom = isWindRandom
            this.isWindChange = isWindChange
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
         * 设置物体大小
         * @param w
         * @param h
         * @param isRandomSize 物体初始大小比例是否随机
         * @return
         */
        fun setSize(w: Int, h: Int, isRandomSize: Boolean): Builder {
            bitmap = changeBitmapSize(bitmap, w, h)
            isSizeRandom = isRandomSize
            return this
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
        moveX()
        moveY()
        if (presentY > mParentHeight || presentX < -bitmap.width || presentX > mParentWidth + bitmap.width) {
            reset()
        }
    }

    /**
     * X轴上的移动逻辑
     */
    private fun moveX() {
        presentX += defaultWindSpeed * sin(angle.toDouble()).toFloat()
        if (isWindChange) {
            angle += ((if (random.nextBoolean()) -1 else 1).toFloat() * Math.random() * 0.0025).toFloat()
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
        randomSpeed()//记得重置时速度也一起重置，这样效果会好很多
        randomWind()//记得重置一下初始角度，不然雪花会越下越少（因为角度累加会让雪花越下越偏）
    }

    /**
     * 随机风的风向和风力大小比例，即随机物体初始下落角度
     */
    private fun randomWind() {
        angle = if (isWindRandom) {
            ((if (random.nextBoolean()) -1 else 1) * Math.random() * initWindLevel / 50).toFloat()
        } else {
            initWindLevel.toFloat() / 50
        }

        //限制angle的最大最小值
        if (angle > HALF_PI) {
            angle = HALF_PI
        } else if (angle < -HALF_PI) {
            angle = -HALF_PI
        }
    }

    companion object {
        private const val defaultSpeed = 10 //默认下降速度
    }
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