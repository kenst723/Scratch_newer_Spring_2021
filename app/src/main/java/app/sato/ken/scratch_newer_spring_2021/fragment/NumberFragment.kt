package app.sato.ken.scratch_newer_spring_2021.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.activities.NumberSelectActivity
import kotlinx.android.synthetic.main.fragment_number.*
import kotlinx.android.synthetic.main.fragment_number.add


class NumberFragment : Fragment() {

    companion object {
        const val keyF = "keyF"
        const val keyS = "keyS"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        add.setOnClickListener {

            //入力された数字を受け取り
            val subF = first.text.toString()
            val subS = second.text.toString()

            //空白の時は処理をしない
            if (subF == "") {
                Toast.makeText(requireContext(), "数字を入力してください", Toast.LENGTH_SHORT).show()
            } else if (subS == "") {
                Toast.makeText(requireContext(), "数字を入力してください", Toast.LENGTH_SHORT).show()

                //それ以外の時は処理をする
            } else {
                val intent = Intent(requireContext(), NumberSelectActivity::class.java)

                //値受け渡しの定義
                intent.putExtra(keyF, subF)
                intent.putExtra(keyS, subS)

                //アクティビティスタート（画面遷移
                startActivity(intent)
            }
        }


     focusChange(first)
     focusChange(second)
    }

    private fun focusChange(editText: EditText){
        editText.setOnFocusChangeListener{view, _ ->
            val inputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}