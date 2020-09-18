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
        val points = getPoints4()
        pathDrawerView.start(points)
    }

    private fun getPoints1(): MutableList<PointF> {
        val points = mutableListOf<PointF>()
        points.add(PointF(0.1f, 0.1f))
        points.add(PointF(0.9f, 0.1f))
        points.add(PointF(0.1f, 0.9f))
        points.add(PointF(0.9f, 0.9f))
        points.add(PointF(0.1f, 0.1f))
        return points
    }

    /**
     * 随机取点
     */
    private fun getPoints2(): MutableList<PointF> {
        val points = mutableListOf<PointF>()
        (1..9).forEach {
            points.add(PointF((1..100).random() / 100.toFloat(), (1..100).random() / 100.toFloat()))
        }
        return points
    }

    /**
     * 五角星
     */
    private fun getPoints3(): MutableList<PointF> {
        val points = mutableListOf<PointF>()
        points.add(PointF(0.28f, 0.43f))
        points.add(PointF(0.7f, 0.43f))
        points.add(PointF(0.36f, 0.56f))
        points.add(PointF(0.49f, 0.35f))
        points.add(PointF(0.64f, 0.56f))
        points.add(PointF(0.28f, 0.43f))
        return points
    }

    /**
     * 圣诞树
     */
    private fun getPoints4(): MutableList<PointF> {
        val points = mutableListOf<PointF>()
        points.add(PointF(0.47f,0.32f))
        points.add(PointF(0.32f,0.39f))
        points.add(PointF(0.39f,0.39f))
        points.add(PointF(0.27f,0.46f))
        points.add(PointF(0.37f,0.46f))
        points.add(PointF(0.25f,0.53f))
        points.add(PointF(0.46f,0.53f))
        points.add(PointF(0.46f,0.61f))
        points.add(PointF(0.49f,0.61f))
        points.add(PointF(0.49f,0.54f))
        points.add(PointF(0.69f,0.54f))
        points.add(PointF(0.6f,0.47f))
        points.add(PointF(0.65f,0.47f))
        points.add(PointF(0.55f,0.4f))
        points.add(PointF(0.61f,0.4f))
        points.add(PointF(0.47f,0.32f))
        return points
    }
}