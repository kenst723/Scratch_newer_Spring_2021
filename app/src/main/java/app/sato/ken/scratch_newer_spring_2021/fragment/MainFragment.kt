package app.sato.ken.scratch_newer_spring_2021.fragment

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.sato.ken.scratch_newer_spring_2021.R
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 * Use the [mainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kodomoFont: Typeface = Typeface.createFromAsset(activity!!.assets, "KodomoRounded.otf")

        /*number.typeface = kodomoFont
        string.typeface = kodomoFont

        //数字でスクラッチ（画面遷移
        number.setOnClickListener {
            val intent = Intent(
                requireContext(),
                NumberFragment::class.java
            )
            startActivity(intent)
        }

        //名前でスクラッチ（画面遷移
        string.setOnClickListener {
            val intent = Intent(
               *//* applicationContext,
                ListActivity::class.java*//*
            )
            startActivity(intent)
        }*/
    }

}