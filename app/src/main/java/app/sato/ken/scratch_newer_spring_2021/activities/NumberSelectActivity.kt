package app.sato.ken.scratch_newer_spring_2021.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.function.ShowSnackBar
import app.sato.ken.scratch_newer_spring_2021.fragment.NumberFragment
import kotlinx.android.synthetic.main.activity_number_select.*
import kotlinx.android.synthetic.main.activity_number_select.finalShowFrag
import kotlinx.android.synthetic.main.activity_number_select.stop

class NumberSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_select)


        //入力されたテキストを受け取る
        val message: String? = intent.getStringExtra(NumberFragment.keyF)
        val message2: String? = intent.getStringExtra(NumberFragment.keyS)
        val snack = ShowSnackBar()

        //受け取ったテキスト2つをStringにしてIntに変換
        val intF = Integer.parseInt(message.toString())
        val intS = Integer.parseInt(message2.toString())

        //入力された数字をランダムに選出
        val random = (intF..intS).random()
        //ランダムテキストをTextViewにセット
        finalShowFrag.text = random.toString()
        //メッセージ
        snack.show("こすって削ってね!",
            "OK",
            number_select_snack,
            applicationContext.resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.colorSnackBarActTextColor)
        )

        stop.setOnClickListener {
            //画面を閉じる
        finish()
        }
    }
}