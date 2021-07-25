package app.sato.ken.scratch_newer_spring_2021.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.ShowSnackBar
import app.sato.ken.scratch_newer_spring_2021.fragment.NameFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_name_select.*
import kotlinx.android.synthetic.main.fragment_name.*

class NameSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_select)
        val snack = ShowSnackBar()

        snack.show("こすって削ってね!",
            "OK",
            name_select_snack,
            applicationContext.resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorSnackBarActTextColor)
        )


        //テキストを受けとる
        val listContext: String? = intent.getStringExtra(NameFragment.randomList)
        finalShowFrag.text = listContext

        stop.setOnClickListener {
            finish()
        }
    }
}