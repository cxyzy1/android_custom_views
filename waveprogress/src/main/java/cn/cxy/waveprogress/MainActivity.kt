package cn.cxy.waveprogress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        waveProgressView.setTextView(text_progress)
        waveProgressView.setOnAnimationListener(object : WaveProgressView.OnAnimationListener {
            override fun howToChangeText(
                interpolatedTime: Float,
                updateNum: Float,
                maxNum: Float
            ): String {
                val decimalFormat = DecimalFormat("0.00")
                return decimalFormat.format(interpolatedTime * updateNum / maxNum * 100)
                    .toString() + "%"
            }

            override fun howToChangeWaveHeight(percent: Float, waveHeight: Float): Float {
                return (1 - percent) * waveHeight;
            }
        })
        waveProgressView.setProgressNum(100f, 1500)

    }
}
