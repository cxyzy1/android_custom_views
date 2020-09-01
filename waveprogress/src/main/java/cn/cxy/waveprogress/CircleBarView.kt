package cn.cxy.waveprogress

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextView
import cn.cxy.waveprogress.utils.DpOrPxUtils

/**
 * Created by anlia on 2017/10/10.
 */
class CircleBarView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var bgPaint: Paint? = null //绘制背景圆弧的画笔
    private var progressPaint: Paint? = null //绘制圆弧的画笔
    private var mRectF: RectF? = null //绘制圆弧的矩形区域
    private var anim: CircleBarAnim? = null
    private var progressNum = 0f //可以更新的进度条数值
    private var maxNum = 0f //进度条最大值
    private var progressColor = 0 //进度条圆弧颜色
    private var bgColor = 0 //背景圆弧颜色
    private var startAngle = 0f //背景圆弧的起始角度
    private var sweepAngle = 0f //背景圆弧扫过的角度
    private var barWidth = 0f //圆弧进度条宽度
    private var defaultSize = 0 //自定义View默认的宽高
    private var progressSweepAngle = 0f //进度条圆弧扫过的角度
    private var textView: TextView? = null
    private var onAnimationListener: OnAnimationListener? = null

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CircleBarView)
        progressColor = typedArray.getColor(
            R.styleable.CircleBarView_progress_color,
            Color.GREEN
        )
        bgColor =
            typedArray.getColor(R.styleable.CircleBarView_bg_color, Color.GRAY)
        startAngle = typedArray.getFloat(R.styleable.CircleBarView_start_angle, 0f)
        sweepAngle = typedArray.getFloat(R.styleable.CircleBarView_sweep_angle, 360f)
        barWidth = typedArray.getDimension(
            R.styleable.CircleBarView_bar_width,
            DpOrPxUtils.dip2px(context, 10f).toFloat()
        )
        typedArray.recycle()
        progressNum = 0f
        maxNum = 100f
        defaultSize = DpOrPxUtils.dip2px(context, 100f)
        mRectF = RectF()
        progressPaint = Paint()
        progressPaint!!.style = Paint.Style.STROKE //只描边，不填充
        progressPaint!!.color = progressColor
        progressPaint!!.isAntiAlias = true //设置抗锯齿
        progressPaint!!.strokeWidth = barWidth
        progressPaint!!.strokeCap = Paint.Cap.ROUND //设置画笔为圆角
        bgPaint = Paint()
        bgPaint!!.style = Paint.Style.STROKE //只描边，不填充
        bgPaint!!.color = bgColor
        bgPaint!!.isAntiAlias = true //设置抗锯齿
        bgPaint!!.strokeWidth = barWidth
        bgPaint!!.strokeCap = Paint.Cap.ROUND
        anim = CircleBarAnim()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = measureSize(defaultSize, heightMeasureSpec)
        val width = measureSize(defaultSize, widthMeasureSpec)
        val min = Math.min(width, height) // 获取View最短边的长度
        setMeasuredDimension(min, min) // 强制改View为以最短边为长度的正方形
        if (min >= barWidth * 2) {
            mRectF!![barWidth / 2, barWidth / 2, min - barWidth / 2] = min - barWidth / 2
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(mRectF!!, startAngle, sweepAngle, false, bgPaint!!)
        canvas.drawArc(mRectF!!, startAngle, progressSweepAngle, false, progressPaint!!)
    }

    inner class CircleBarAnim : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) { //interpolatedTime从0渐变成1,到1时结束动画,持续时间由setDuration（time）方法设置
            super.applyTransformation(interpolatedTime, t)
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum
            if (onAnimationListener != null) {
                if (textView != null) {
                    textView!!.text = onAnimationListener!!.howToChangeText(
                        interpolatedTime,
                        progressNum,
                        maxNum
                    )
                }
                onAnimationListener!!.howTiChangeProgressColor(
                    progressPaint,
                    interpolatedTime,
                    progressNum,
                    maxNum
                )
            }
            postInvalidate()
        }
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize)
        }
        return result
    }

    /**
     * 设置进度条最大值
     * @param maxNum
     */
    fun setMaxNum(maxNum: Float) {
        this.maxNum = maxNum
    }

    /**
     * 设置进度条数值
     * @param progressNum 进度条数值
     * @param time 动画持续时间
     */
    fun setProgressNum(progressNum: Float, time: Int) {
        this.progressNum = progressNum
        anim!!.duration = time.toLong()
        startAnimation(anim)
    }

    /**
     * 设置显示文字的TextView
     * @param textView
     */
    fun setTextView(textView: TextView?) {
        this.textView = textView
    }

    interface OnAnimationListener {
        /**
         * 如何处理要显示的文字内容
         * @param interpolatedTime 从0渐变成1,到1时结束动画
         * @param updateNum 进度条数值
         * @param maxNum 进度条最大值
         * @return
         */
        fun howToChangeText(
            interpolatedTime: Float,
            updateNum: Float,
            maxNum: Float
        ): String?

        /**
         * 如何处理进度条的颜色
         * @param paint 进度条画笔
         * @param interpolatedTime 从0渐变成1,到1时结束动画
         * @param updateNum 进度条数值
         * @param maxNum 进度条最大值
         */
        fun howTiChangeProgressColor(
            paint: Paint?,
            interpolatedTime: Float,
            updateNum: Float,
            maxNum: Float
        )
    }

    fun setOnAnimationListener(onAnimationListener: OnAnimationListener?) {
        this.onAnimationListener = onAnimationListener
    }
}