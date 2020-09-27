package cn.cxy.stars

import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var starWidth = 10
    private var startHeight = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateChildViews(rootLayout)
        rootLayout.setOnClickListener { updateChildViews(rootLayout) }
    }

    private fun updateChildViews(viewGroup: ViewGroup) {
        viewGroup.removeAllViews()
        viewGroup.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                viewGroup.viewTreeObserver.removeOnPreDrawListener(this)
                (0..100).forEach {
                    addStarView(getRandomRatio(), getRandomRatio(), viewGroup.width, viewGroup.height, starWidth, startHeight)
                }
                return true
            }
        })
    }

    private fun getRandomRatio() = ((1..100).random() / 100.toFloat())

    private fun addStarView(x: Float, y: Float, parentWidth: Int, parentHeight: Int, width: Int, height: Int) {
        val view = LightSpotViewWithAnimation(this)
        val layoutParams = FrameLayout.LayoutParams(dp2Px(width), dp2Px(height))
        layoutParams.leftMargin = (x * parentWidth).toInt()
        layoutParams.topMargin = (y * parentHeight).toInt()
        rootLayout.addView(view, layoutParams)
    }

    fun dp2Px(dpValue: Int) = dp2Px(this, dpValue)
}