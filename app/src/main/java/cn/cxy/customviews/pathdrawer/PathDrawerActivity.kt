package cn.cxy.customviews.pathdrawer

import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.cxy.customviews.R
import kotlinx.android.synthetic.main.activity_path_drawer.*

class PathDrawerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_drawer)
        val points = mutableListOf<PointF>()
        points.add(PointF(0.1f,0.1f))
        points.add(PointF(0.9f,0.1f))
        points.add(PointF(0.9f,0.9f))
        points.add(PointF(0.1f,0.9f))
        points.add(PointF(0.1f,0.1f))
        pathDrawerView.start(points)
    }
}