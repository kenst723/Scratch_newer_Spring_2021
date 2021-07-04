package app.sato.ken.scratch_newer_spring_2021.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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
    var dataList1 = mutableListOf<RowModel>()
    private val dataList = mutableListOf<RowModel>()
    var resultList = mutableListOf<String>()
    private val adapter = ViewAdapter(dataList, object : ViewAdapter.ListListener {
        override fun onClickRow(tappedView: View, rowModel: RowModel) {
            Toast.makeText(requireContext(), rowModel.title, Toast.LENGTH_SHORT).show()
        }
    })

    companion object {
        const val randomList = "random"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_name, container, false)
    }

    override fun onStop() {
        super.onStop()
        resultList.removeAll(resultList)
        adapter.notifyDataSetChanged()
        dataList1.removeAll(dataList1)
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerview = history_view
        val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(adapter)
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerview)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())

        add.setOnClickListener {
            var num = ""

            if (content.text.isNotEmpty()) {
                val text = content.text.toString()
                val data: RowModel = RowModel().also {
                    it.title = text
                    num = it.title
                }

                dataList.add(data)
                resultList.add(num)

                this.dataList1 = dataList

                recyclerview.adapter = adapter
            } else {
                Toast.makeText(
                    requireContext(),
                    "文字を入力してください",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        content.setOnFocusChangeListener { view, _ ->
            val inputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        start.setOnClickListener {

            val intent = Intent(requireContext(), NameSelectActivity::class.java)

            if (dataList.isNotEmpty() or resultList.isNotEmpty()) {
                intent.putExtra(randomList, resultList.random())
                startActivity(intent)
            } else {

                Toast.makeText(
                    requireContext(),
                    "文字を入力してください",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
    }

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

