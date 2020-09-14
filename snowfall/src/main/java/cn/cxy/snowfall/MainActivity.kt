package cn.cxy.snowfall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.cxy.snowfall.desktopSnow.DesktopSnowActivity
import cn.cxy.snowfall.snow.SnowActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snow1.setOnClickListener { startActivity(Intent(this, SnowActivity::class.java)) }
        snow2.setOnClickListener { startActivity(Intent(this, DesktopSnowActivity::class.java)) }
    }
}
