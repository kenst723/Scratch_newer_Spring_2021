package app.sato.ken.scratch_newer_spring_2021

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import app.sato.ken.scratch_newer_spring_2021.fragment.MainFragment
import app.sato.ken.scratch_newer_spring_2021.fragment.NameFragment
import app.sato.ken.scratch_newer_spring_2021.fragment.NumberFragment
import app.sato.ken.scratch_newer_spring_2021.fragment.RouletteFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

var mPosition: Int? = null
lateinit var mTabLayout: TabLayout
@SuppressLint("StaticFieldLeak")
lateinit var mPagerAdapter: MainActivity.SectionsPagerAdapter
lateinit var tabItem: ArrayList<String>

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number.setOnClickListener {
            when(view_pager.currentItem){
                1 ->{
                    view_pager.currentItem -= 1
                }
                2 -> {
                    view_pager.currentItem -= 2
                }
            }
        }

        string.setOnClickListener {

            when(view_pager.currentItem){
                0 ->{
                    view_pager.currentItem += 2
                }
                1 -> {
                    view_pager.currentItem += 1
                }
            }
        }

        number.isVisible
        string.isVisible

        /// スクロール中の変更処理
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            /// implementする
            override fun onPageSelected(position: Int) {

            when(position){
                    0 -> {
                        number.isVisible = false
                        string.isVisible = false
                        roulette.isVisible = false
                    }
                    1 -> {

                        Handler().postDelayed(
                            {
                                number.isVisible = true
                                string.isVisible = true
                                roulette.isVisible = true},
                            10
                        )
                    }
                    2 -> {
                        number.isVisible = false
                        string.isVisible = false
                        roulette.isVisible = false
                    }
                    3 ->{
                        number.isVisible = false
                        string.isVisible = false
                        roulette.isVisible = false
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }
        })

        mTabLayout = findViewById(R.id.tabs)

        mPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        tabItem = ArrayList()

        addTab("数字でスクラッチ")//0
        addTab("ホーム")//1
        addTab("名前でスクラッチ")//2
        addTab("ルーレット")//3

        Handler().postDelayed(
            {
                mTabLayout.getTabAt(1)!!.select() },
            50
        )

        view_pager.adapter  = mPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }


    private fun addTab(title: String){
        mTabLayout.addTab(mTabLayout.newTab().setText(title))
        addTabPage(title)
    }

    private fun addTabPage(title: String){
        tabItem.add(title)
        mPagerAdapter.notifyDataSetChanged()
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
        : FragmentPagerAdapter(fm){

        override fun getItem(position: Int): Fragment {
            return when(position){
                0 -> {
                    mPosition = 0
                    NumberFragment()
                }
                1 -> {
                    mPosition = 1
                    MainFragment()
                }
                2 ->{
                    mPosition = 2
                    NameFragment()
                }
                else ->{
                    mPosition = 3
                    RouletteFragment()
                }
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabItem[position]
        }

        override fun getCount(): Int {
          return tabItem.size
        }
    }
}