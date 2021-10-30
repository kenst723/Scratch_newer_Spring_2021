package app.sato.ken.scratch_newer_spring_2021.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.fragment.RouletteFragment
import app.sato.ken.scratch_newer_spring_2021.function.ShowSnackBar
import app.sato.ken.scratch_newer_spring_2021.model.RotateListener
import kotlinx.android.synthetic.main.activity_name_select.*

import kotlinx.android.synthetic.main.activity_roulette.*

class RouletteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)

        val recievedArray: ArrayList<String>?  = intent.getStringArrayListExtra(RouletteFragment.send)
        Log.d("recieved",recievedArray.toString())


        val colors = mutableListOf<String>()
            if (recievedArray != null) {
                for(i in recievedArray){
                    /**
                     * 1..255としないのは、1..255としてしまうと
                     * 16進数変換時に1桁の数となり、有効な色として認識されないため
                     * 1桁の16進数は色に変換されない ->
                     * IllegalArgumentException
                     */
                    val r = (16..255).random()
                    val g = (16..255).random()
                    val b = (16..255).random()
                    val rHex = Integer.toHexString(r)
                    val gHex = Integer.toHexString(g)
                    val bHex = Integer.toHexString(b)

                    colors.add("#$rHex$gHex$bHex")
                }
            }



        roulette.apply {
            setTextColor(R.color.rouletteBlack)
            setRouletteBorderLineColor(R.color.colorSnackBarActTextColor)
            getTextColor()
            setRouletteDataList(recievedArray!!.toList())
            setRoulettesize(recievedArray.size)
            setRouletteColor(colors)
        }

        start.setOnClickListener {
            rotateRoulette()
        }

        retrun.setOnClickListener {
            finish()
        }
    }

    private fun rotateRoulette() {
        val rouletteListener = object : RotateListener {
            override fun onRotateStart() {

            }

            @SuppressLint("SetTextI18n")
            override fun onRotateEnd(result: String) {
                result_text.text = result

                val snack = ShowSnackBar()

                snack.show("結果は $result です!",
                    "OK",
                    roulette_coordinator,
                    applicationContext.resources.getColor(R.color.colorPrimary),
                    resources.getColor(R.color.colorSnackBarActTextColor)
                )
            }
        }

        val toDegrees = (2000..100000).random().toFloat()
        roulette.rotateRoulette(toDegrees, 4000, rouletteListener)
    }
}