package cn.cxy.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        startRunning()

        startBtn.setOnClickListener { startRunning() }
        stopBtn.setOnClickListener { runningLineView.stop() }
    }

    private fun startRunning() {
        val gradientColors = intArrayOf(
            resources.getColor(R.color.color1),
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color3)
        )
        runningLineView
            .setStartPosition(0.2f, 0f)
            .setLineWidth(5f)
            .setGradient(gradientColors)
            .setStepSize(5)
            .setLineLength(200f)
            //            .setLineColorRes(R.color.colorAccent)
            .start()
    }
}