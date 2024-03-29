package app.sato.ken.scratch_newer_spring_2021.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat.IntentBuilder
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.fragment.NameFragment
import app.sato.ken.scratch_newer_spring_2021.function.ShowSnackBar
import kotlinx.android.synthetic.main.activity_name_select.*


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