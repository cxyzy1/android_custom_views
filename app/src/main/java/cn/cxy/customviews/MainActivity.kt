package cn.cxy.customviews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.customviews.misc.MiscActivity
import cn.cxy.customviews.runningline.RunningLineActivity2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { startActivity(Intent(this, MiscActivity::class.java)) }
        runningLineBtn.setOnClickListener { startActivity(Intent(this, RunningLineActivity2::class.java)) }
    }
}