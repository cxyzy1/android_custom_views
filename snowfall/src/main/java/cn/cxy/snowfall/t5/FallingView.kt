package cn.cxy.customviews.fall.t5

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.core.content.ContextCompat
import cn.cxy.customviews.fall.t6.FallObject
import cn.cxy.snowfall.R
import kotlin.math.min


/**
 * 圆点飘落，每次降落前x坐标会改变
 */
class FallingView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mViewWidth = 0
    private var mViewHeight = 0
    private val defaultWidth = 600 //默认宽度
    private val defaultHeight = 1000 //默认高度
    private val intervalTime = 5 //重绘间隔时间
    private val fallObjects: MutableList<FallObject>

    init {
        fallObjects = ArrayList()
        val snowPaint = Paint()
        snowPaint.color = Color.WHITE
        snowPaint.style = Paint.Style.FILL
        val bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
        val bitmapCanvas = Canvas(bitmap);
        bitmapCanvas.drawCircle(25f, 25f, 25f, snowPaint);

        val builder = FallObject.Builder(ContextCompat.getDrawable(context, R.drawable.ic_snow)!!)
        val fallObject = builder.setSpeed(10,true).setSize(50, 50,true).build()

        addFallObject(fallObject, 50)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureSize(defaultHeight, heightMeasureSpec)
        val width = measureSize(defaultWidth, widthMeasureSpec)
        setMeasuredDimension(width, height)
        mViewWidth = width
        mViewHeight = height
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = min(result, specSize)
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (fallObjects.size > 0) {
            for (i in 0 until fallObjects.size) {
                fallObjects[i].drawObject(canvas)
            }
            // 隔一段时间重绘一次, 动画效果
            handler.postDelayed(runnable, intervalTime.toLong())
        }
    }

    // 重绘线程
    private val runnable = Runnable {
        invalidate()
    }

    /**
     * 向View添加下落物体对象
     * @param fallObject 下落物体对象
     * @param num
     */
    private fun addFallObject(fallObject: FallObject, num: Int) {
        viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewTreeObserver.removeOnPreDrawListener(this)
                for (i in 0 until num) {
                    val newFallObject = FallObject(fallObject.mBuilder, mViewWidth, mViewHeight)
                    fallObjects.add(newFallObject)
                }
                return true
            }
        })
    }
}