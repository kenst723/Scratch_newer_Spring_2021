package app.sato.ken.scratch_newer_spring_2021.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.activities.NameSelectActivity
import app.sato.ken.scratch_newer_spring_2021.model.RowModel
import app.sato.ken.scrtch.adapter.HomeViewHolder
import app.sato.ken.scrtch.adapter.ViewAdapter
import kotlinx.android.synthetic.main.fragment_name.*

class NameFragment : Fragment() {

    val text = ""
    var dataList = mutableListOf<RowModel>()
    var resultList = mutableListOf<String>()

    companion object {
        const val randomList = "random"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataList = mutableListOf<RowModel>()


        //recyclerViewのIDを変数に入れる
        val recyclerview = history_view

        //recyclerViewのadapter定義
        val adapter = ViewAdapter(dataList, object : ViewAdapter.ListListener {
            override fun onClickRow(tappedView: View, rowModel: RowModel) {
                Toast.makeText(requireContext(), rowModel.title, Toast.LENGTH_SHORT).show()
            }
        })

        //スワイプ・ドラッグの設定
        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter)
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())


        //追加ボタンクリックの処理
        add.setOnClickListener {
            var num = ""

            //入力されたテキストが空ではないとき
            if (content.text.isNotEmpty()) {
                //RecyclerViewにRowModel型のテキストを定義
                val text = content.text.toString()
                val data: RowModel = RowModel().also {
                    it.title = text
                    num = it.title
                }

                //配列にテキストをセット
                dataList.add(data)
                resultList.add(num) //ランダムに選ぶ用の配列(String型のmutableListOf)

                //グローバル変数の配列に代入
                this.dataList = dataList

                //アダプターをセット
                recyclerview.adapter = adapter
            } else {
                //入力されたテキストが空白の時
                //Toastメッセージを送る
                Toast.makeText(
                    requireContext(),
                    "文字を入力してください",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

       //テンキーを閉じる設定 or レイアウトを保存して表示
        content.setOnFocusChangeListener { view, _ ->
            val inputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        //スタートボタンクリック時
        start.setOnClickListener {

            //画面遷移
            val intent = Intent(requireContext(), NameSelectActivity::class.java)

            //配列が空白ではないとき
            if (dataList.isNotEmpty() or resultList.isNotEmpty()) {
                //キーを受け渡し
                intent.putExtra(randomList, resultList.random())
                //アクティビティ（画面）を遷移
                startActivity(intent)
            } else {

                //空白の時は遷移しないでToast
                Toast.makeText(
                    requireContext(),
                    "文字を入力してください",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
    }


    //スライドした時の処理
    private fun getSwipeToDismissTouchHelper(adapter: RecyclerView.Adapter<HomeViewHolder>) =
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,             //p0
                viewHolder: RecyclerView.ViewHolder,    //p1
                target: RecyclerView.ViewHolder         //p2
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                /*
                * ドラッグ時、viewType が異なるアイテムを超えるときに、
                * notifyItemMoved を呼び出すと、ドラッグ操作がキャンセルされてしまう。
                *
                * 同じ ViewType アイテムを超える時だけ notifyItemMoved を呼び出す。
                * */
                if (viewHolder.itemViewType == target.itemViewType) {
                    // Adapter の持つ実データセットを操作している
                    dataList.add(toPosition, dataList.removeAt(fromPosition))
                    // Adapter にアイテムが移動したことを通知
                    adapter.notifyItemMoved(fromPosition, toPosition)
                }

                return true
            }

            //スワイプしたときに呼び出される処理
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //データを削除
                dataList.removeAt(viewHolder.adapterPosition)
                resultList.removeAt(viewHolder.adapterPosition)

                //アダプターに通知
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })

    override fun onStop() {
        super.onStop()
        resultList.removeAll(resultList)
    }
}