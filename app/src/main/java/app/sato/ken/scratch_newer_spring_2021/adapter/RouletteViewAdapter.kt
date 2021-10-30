package app.sato.ken.scratch_newer_spring_2021.adapter

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import app.sato.ken.scratch_newer_spring_2021.R
import app.sato.ken.scratch_newer_spring_2021.model.RowModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class RouletteViewAdapter(
    private val fragmentManager: FragmentManager?,
    private val ctx: Context,
    private val list: MutableList<RowModel>, private val listener: ListListener) :
    androidx.recyclerview.widget.RecyclerView.Adapter<RouletteHomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouletteHomeViewHolder {
        Log.d("Life Cycle", "onCreateViewHolder")
        val rowView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.simple_item_roulette, parent, false)
        return RouletteHomeViewHolder(rowView)
    }

    override fun onBindViewHolder(holder: RouletteHomeViewHolder, position: Int) {
        Log.d("Life Cycle", "onBindViewHolder")
        holder.titleView.text = list[position].title
        holder.itemView.setOnClickListener {
            listener.onClickRow(it, list[position])
        }
    }

    override fun getItemCount(): Int {
        Log.d("Life Cycle", "getItemCount")
        return list.size
    }

    interface ListListener {
        fun onClickRow(tappedView: View, rowModel: RowModel)
    }
}