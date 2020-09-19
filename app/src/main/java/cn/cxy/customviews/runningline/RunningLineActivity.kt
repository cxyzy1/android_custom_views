package cn.cxy.customviews.runningline

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.customviews.R
import kotlinx.android.synthetic.main.activity_running_line.*

class RunningLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running_line)
        runningLineView
            .setStepSize(10)
            .setLineWidth(10f)
            .setLineColor(Color.BLUE)
            .start()
    }
}