package com.example.a7minutesworkout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import kotlinx.android.synthetic.main.item_history_row.view.*

class HistoryAdapter(val context: Context, val items: ArrayList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val llHistoryMainItem = view.ll_history_item_main
        val tvItem = view.tvItem
        val tvPostion = view.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder : HistoryAdapter.ViewHolder = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_row, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: String = items.get(position)
        holder.tvPostion.text = (position + 1).toString()
        holder.tvItem.text = date
        if(position % 2 == 0) {
            holder.llHistoryMainItem.setBackgroundColor(Color.parseColor("#EBEBEB"))
        } else {
            holder.llHistoryMainItem.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


}