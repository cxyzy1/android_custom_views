package cn.cxy.qqstepview

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        qqStepView.setStepMax(5000)
        //属性动画
        val animator = ValueAnimator.ofFloat(0f, 3000f) //0到3000的变化

        animator.addUpdateListener { animation ->
            val currentStep = animation.animatedValue as Float //获取当前值
            qqStepView.setCurrentStep(currentStep.toInt())
        }
        animator.duration = 2000
        animator.start()

    }
}
