package app.sato.ken.scratch_newer_spring_2021.activities

import android.app.Application
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.fragment.RouletteFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class RouletteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        val recievedArray: ArrayList<String>?  = intent.getStringArrayListExtra(RouletteFragment.send)
        Log.d("recieved",recievedArray.toString())

        val pieChart = findViewById<PieChart>(R.id.piechart)

        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = false
        pieChart.setHoleColor(Color.BLACK)
        pieChart.transparentCircleRadius = 61f

        val yValues = ArrayList<PieEntry>()


        for(i in recievedArray!!){
            yValues.add(PieEntry(1f,i))
        }

        val description = Description()
        description.text = ""

        description.textSize = 20f
        pieChart.description = description

        //pieChart.animateXY(1000, 1000)


        val dataSet = PieDataSet(yValues, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.setColors(*ColorTemplate.JOYFUL_COLORS)

        val data = PieData(dataSet)
        data.setValueTextSize(0f)
        data.setValueTextColor(Color.BLACK)

        pieChart.data = data
    }
}