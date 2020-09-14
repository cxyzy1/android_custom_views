package cn.cxy.customviews.misc

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import cn.cxy.customviews.util.dp2Px

/**
 * 星形，带模糊效果
 */
class StarView2(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val mPaint = Paint()
    private val strokeWidth = dp2Px(context, 4f)
    private val path = Path()

    init {
        //设置实心
        mPaint.style = Paint.Style.FILL
        //设置颜色
        mPaint.color = Color.BLUE
        //设置线宽
        mPaint.strokeWidth = strokeWidth
        // 设置画笔的锯齿效果
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawStar(canvas)
    }

    private fun drawStar(canvas: Canvas) {
        setPaintStyle(true)
        val blurRadius = width / 20.toFloat()
        mPaint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
        val xxSize = dp2Px(context, 1f)
        path.moveTo(width / 2.toFloat(), 0f)
        path.lineTo(width / 2.toFloat() + xxSize, height.toFloat() / 2 - xxSize)
        path.lineTo(width.toFloat(), height.toFloat() / 2)
        path.lineTo(width / 2.toFloat() + xxSize, height.toFloat() / 2 + xxSize)
        path.lineTo(width / 2.toFloat(), height.toFloat())
        path.lineTo(width / 2.toFloat() - xxSize, height.toFloat() / 2 + xxSize)
        path.lineTo(0f, height / 2.toFloat())
        path.lineTo(width / 2.toFloat() - xxSize, height.toFloat() / 2 - xxSize)
        path.close()
        canvas.drawPath(path, mPaint)
    }

    private fun setPaintStyle(isFilled: Boolean) {
        mPaint.style = if (isFilled) Paint.Style.FILL else Paint.Style.STROKE
    }

    fun anim() {
        val srcView = this
        val animateTime = 2000L
        val scaleDownAnimation = ScaleAnimation(
            1f, 0.2f, 1f, 0.2f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleDownAnimation.fillAfter = true
        scaleDownAnimation.duration = animateTime


        val scaleUpAnimation = ScaleAnimation(
            0.2f, 1f, 0.2f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleUpAnimation.fillAfter = true
        scaleUpAnimation.duration = animateTime

        scaleDownAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                srcView.startAnimation(scaleUpAnimation)
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        scaleUpAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                srcView.startAnimation(scaleDownAnimation)
            }

            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })



        srcView.startAnimation(scaleDownAnimation)
    }
}