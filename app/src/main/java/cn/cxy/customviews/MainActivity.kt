package cn.cxy.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        runningLineView
            .setLineWidth(20f)
            .setStartPosition(0f,0.5f)
            .setStepSize(5)
            .setLineColorRes(R.color.colorAccent)
            .start()
        startBtn.setOnClickListener {

        }
        stopBtn.setOnClickListener { runningLineView.stop() }
    }
}