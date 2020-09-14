package cn.cxy.customviews.runningline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.cxy.customviews.R
import kotlinx.android.synthetic.main.activity_running_line.*

class RunningLineActivity : AppCompatActivity() {
    lateinit var gradientColors: IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running_line)
        gradientColors = intArrayOf(
            resources.getColor(R.color.color1),
            resources.getColor(R.color.color2),
            resources.getColor(R.color.color3)
        )

        startRunning()

        startBtn.setOnClickListener { startRunning() }
        stopBtn.setOnClickListener { stopRunning() }
    }


    private fun startRunning() {
        startRunning(runningLineView)
        startRunning(runningLineView2)
    }

    private fun startRunning(runningLineView: RunningLineView) {
        runningLineView
            .setStartPosition(0.2f, 0f)
            .setLineWidth(5f)
            .setGradient(gradientColors)
            .setStepSize(5)
            .setLineLength(100f)
            //            .setLineColorRes(R.color.colorAccent)
            .start()
    }

    private fun stopRunning() {
        runningLineView.stop()
    }
}