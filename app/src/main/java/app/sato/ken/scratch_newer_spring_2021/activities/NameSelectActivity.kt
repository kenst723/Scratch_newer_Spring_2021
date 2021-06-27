package app.sato.ken.scratch_newer_spring_2021.activities

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.fragment.NameFragment
import kotlinx.android.synthetic.main.activity_name_select.*

class NameSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_select)



        //Toast送信
        Toast.makeText(applicationContext, "おめでとう!", Toast.LENGTH_SHORT).show()
        //フォント設定
        val kodomoFont: Typeface = Typeface.createFromAsset(assets, "KodomoRounded.otf")

        //テキストを受けとる
        val listContext: String? = intent.getStringExtra(NameFragment.randomList)
        //フォント
        finalShowFrag.typeface = kodomoFont
        //テキストにセット（まだ隠れている状態です。
        // 削ることで見える仕組み）
        finalShowFrag.text = listContext

        stop.setOnClickListener {
            finish()
        }
        //ボタンテキストのフォント
        stop.typeface = kodomoFont
    }
}