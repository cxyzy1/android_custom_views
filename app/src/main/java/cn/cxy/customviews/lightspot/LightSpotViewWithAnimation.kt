package cn.cxy.customviews.lightspot

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import cn.cxy.customviews.dp2Px
import cn.cxy.customviews.minValue

class LightSpotViewWithAnimation(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private var mPaint = Paint()
    private var centerX = 0F
    private var centerY = 0F
    private var defaultSize = 200F
    private val intervalTime = 5 //重绘间隔时间
    private var mWidth = defaultSize.toInt()
    private var mHeight = defaultSize.toInt()
    private var minSize = defaultSize.toInt()

    //圆点大小
    private var radius = 50F

    //发光范围
    private var blurRadius = 50F

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint.color = Color.parseColor("#FFFFFF")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        mWidth = startMeasure(widthMeasureSpec)
        mHeight = startMeasure(heightMeasureSpec)
        setMeasuredDimension(mWidth, mHeight)
        centerX = mWidth / 2.toFloat()
        centerY = mHeight / 2.toFloat()

        minSize = minValue(mWidth, mHeight)
        setRadiusSizes(minSize)
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

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

        canvas.drawCircle(centerX, centerY, radius, mPaint)
        // 隔一段时间重绘一次, 动画效果
        handler.postDelayed(runnable, intervalTime.toLong())
    }

    //当前是否处于放大过程中
    var isIncreasing = false

    // 重绘线程
    private val runnable = Runnable {
        smoothChangeRadiusSize()
        invalidate()
    }

    /**
     * 先缩小，后放大，营造呼吸效果
     */
    private fun smoothChangeRadiusSize() {
        if (isIncreasing) {
            minSize++
        } else {
            minSize--
        }
        if (minSize <= 0) {
            minSize++
            isIncreasing = true
        } else if (minSize >= minValue(mWidth, mHeight)) {
            minSize--
            isIncreasing = false
        }
        setRadiusSizes(minSize)
    }

    private fun setRadiusSizes(baseSize: Int) {
        radius = baseSize / 4.toFloat()
        blurRadius = baseSize / 5.toFloat()
    }
}