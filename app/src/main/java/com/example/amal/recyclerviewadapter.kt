package com.example.amal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class recyclerviewadapter (val money:ArrayList<String>):RecyclerView.Adapter <recyclerviewadapter.ItemViewHolder>() {
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder (itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val oneop = money[position]
        holder.itemView.apply { tvmoney.text = oneop }
    }

    override fun getItemCount() = money.size

    fun deleteItems(){
        money.removeAll {true}
        notifyDataSetChanged()
    }
}
