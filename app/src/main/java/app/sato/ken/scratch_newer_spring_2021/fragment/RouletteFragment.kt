package app.sato.ken.scratch_newer_spring_2021.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.activities.NameSelectActivity
import app.sato.ken.scratch_newer_spring_2021.activities.RouletteActivity
import app.sato.ken.scratch_newer_spring_2021.adapter.RouletteHomeViewHolder
import app.sato.ken.scratch_newer_spring_2021.adapter.RouletteViewAdapter
import app.sato.ken.scratch_newer_spring_2021.function.ShowSnackBar
import app.sato.ken.scratch_newer_spring_2021.model.RowModel
import kotlinx.android.synthetic.main.fragment_roulette.*

class RouletteFragment : Fragment() {

    var dataList1 = mutableListOf<RowModel>()
    private val snack = ShowSnackBar()
    private val dataList = mutableListOf<RowModel>()
    var resultList = ArrayList<String>()
    private val adapter = RouletteViewAdapter(dataList, object : RouletteViewAdapter.ListListener {
        override fun onClickRow(tappedView: View, rowModel: RowModel) {

        }
    })

    companion object {
        const val send = "send"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roulette, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onDestroyView() {
        super.onDestroyView()

        resultList.removeAll(resultList)
        adapter.notifyDataSetChanged()
        dataList1.removeAll(dataList1)
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = rhistory_view
        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter)
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        radd.setOnClickListener {
            var num = ""

            if (rcontent.text.isNotEmpty()) {
                val text = rcontent.text.toString()
                val data: RowModel = RowModel().also {
                    it.title = text
                    num = it.title
                }

                dataList.add(data)
                resultList.add(num)

                this.dataList1 = dataList

                recyclerview.adapter = adapter
            } else {
                snack.show("文字を入力してください",
                    "OK",
                    rposView1,
                    requireContext().resources.getColor(R.color.colorPrimary),
                    resources.getColor(R.color.colorSnackBarActTextColor)
                )
            }
        }

        rcontent.setOnFocusChangeListener {view, _ ->
            val inputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        rstart.setOnClickListener {

            val intent = Intent(requireContext(), RouletteActivity::class.java)

            if (dataList.isNotEmpty() or resultList.isNotEmpty()) {
                intent.putStringArrayListExtra(send, resultList)
                startActivity(intent)
                Log.d("sending", resultList.toString())
            } else {
                snack.show("文字を入力してください",
                    "OK",
                    rposView1,
                    requireContext().resources.getColor(R.color.colorPrimary),
                    resources.getColor(R.color.colorSnackBarActTextColor)
                )
            }
        }
    }

    private fun getSwipeToDismissTouchHelper(adapter: RecyclerView.Adapter<RouletteHomeViewHolder>) =
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
                if (viewHolder.itemViewType == target.itemViewType) {
                    dataList1.add(toPosition, dataList1.removeAt(fromPosition))
                    adapter.notifyItemMoved(fromPosition, toPosition)
                }

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                dataList1.removeAt(viewHolder.adapterPosition)
                resultList.removeAt(viewHolder.adapterPosition)

                adapter.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
}