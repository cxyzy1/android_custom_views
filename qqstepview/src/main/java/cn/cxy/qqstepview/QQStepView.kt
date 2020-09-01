package cn.cxy.qqstepview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * 来源：https://www.jianshu.com/p/36e3f949177b
 */
class QQStepView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mOuterColor = Color.RED
    private var mInnerColor = Color.BLUE
    private var mBorderWidth = 20
    private var mStepTextSize = 0
    private var mStepTextColor = 0
    private val mOuterPaint: Paint
    private val mInnerPaint: Paint
    private val mTextPaint: Paint
    private var mStepMax = 0 //总的步数
    private var mCurrentStep = 0 //当前步数

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //调用者在布局文件中可能 wrap_content导致宽高不一致
        //确保是正方形
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            if (width > height) height else width,
            if (width > height) height else width
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //1.画外圆弧   边缘没有显示完整
        //RectF recf = new RectF(0, 0, getWidth(), getHeight());
        val center = width / 2
        val radius = width / 2 - mBorderWidth / 2
        //mBorderWidth/2,mBorderWidth/2,getWidth()-mBorderWidth/2,getWidth()-mBorderWidth/2
        val recf = RectF(
            (center - radius).toFloat(),
            (center - radius).toFloat(),
            (center + radius).toFloat(),
            (center + radius).toFloat()
        )
        canvas.drawArc(recf, 135f, 270f, false, mOuterPaint)
        //2.绘制内圆弧
        val sweepAngle = mCurrentStep.toFloat() / mStepMax
        canvas.drawArc(recf, 135f, sweepAngle * 270, false, mInnerPaint)

        //3.绘制文字
        val stepText = mCurrentStep.toString() + ""
        val rect = Rect()
        mTextPaint.getTextBounds(stepText, 0, stepText.length, rect)
        val dx = width / 2 - rect.width() / 2
        //第一种方式获取高度
        //int dy = getWidth() / 2 + rect.width()/2;
        //第二种表达方式获取高度
        val fontMetrics = mTextPaint.fontMetricsInt
        //获取中心(fontMetrics.bottom - fontMetrics.top) / 2
        val dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseLine = height / 2 + dy
        canvas.drawText(stepText, dx.toFloat(), baseLine.toFloat(), mTextPaint)
    }

    //其他，写几个方法让他动起来
    fun setStepMax(mStepMax: Int) {
        this.mStepMax = mStepMax
    }

    fun setCurrentStep(mCurrentStep: Int) {
        this.mCurrentStep = mCurrentStep
        //不断绘制 onDraw()
        invalidate()
    }

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.QQStepView)
        mOuterColor = ta.getColor(R.styleable.QQStepView_outerColor, mOuterColor)
        mInnerColor = ta.getColor(R.styleable.QQStepView_innerColor, mInnerColor)
        mBorderWidth =
            ta.getDimension(R.styleable.QQStepView_borderWidth, mBorderWidth.toFloat()).toInt()
        mStepTextSize =
            ta.getDimensionPixelOffset(R.styleable.QQStepView_stepTextSize, mStepTextSize)
        mStepTextColor = ta.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor)
        ta.recycle()
        //内弧
        mOuterPaint = Paint()
        mOuterPaint.isAntiAlias = true
        mOuterPaint.strokeWidth = mBorderWidth.toFloat()
        mOuterPaint.color = mOuterColor
        mOuterPaint.strokeCap = Paint.Cap.ROUND //设置下方为圆形
        mOuterPaint.style = Paint.Style.STROKE //设置内部为空心
        //外弧
        mInnerPaint = Paint()
        mInnerPaint.isAntiAlias = true
        mInnerPaint.strokeWidth = mBorderWidth.toFloat()
        mInnerPaint.color = mInnerColor
        mInnerPaint.strokeCap = Paint.Cap.ROUND //设置下方为圆形
        mInnerPaint.style = Paint.Style.STROKE //设置内部为空心

        //文字
        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = mStepTextSize.toFloat()
        mTextPaint.color = mStepTextColor
    }
}