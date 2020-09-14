package cn.cxy.customviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.cxy.customviews.misc.MiscActivity
import cn.cxy.customviews.runningline.RunningLineActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener { startActivity(Intent(this, MiscActivity::class.java)) }
        runningLineBtn.setOnClickListener { startActivity(Intent(this, RunningLineActivity::class.java)) }

    }
}