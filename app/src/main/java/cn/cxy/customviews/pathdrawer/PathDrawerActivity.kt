package cn.cxy.customviews.pathdrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.customviews.R
import kotlinx.android.synthetic.main.activity_path_drawer.*

class PathDrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_drawer)
        pathDrawerView.start()
    }
}