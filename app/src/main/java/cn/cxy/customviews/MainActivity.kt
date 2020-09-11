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
            .setStartPosition(0.2f,0f)
            .setLineWidth(80f)
            .setStepSize(5)
            .setLineLength(100f)
            .setLineColorRes(R.color.colorAccent)
            .start()
        startBtn.setOnClickListener {

        }
        stopBtn.setOnClickListener { runningLineView.stop() }
    }
}