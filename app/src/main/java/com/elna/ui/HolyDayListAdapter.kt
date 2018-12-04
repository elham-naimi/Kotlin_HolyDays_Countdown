package com.elna.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elna.kotlinfragment.R
import com.elna.model.HolyDay
import kotlinx.android.synthetic.main.list_item.view.*

public class HolyDayListAdapter(val context: Context, val onClickListener: View.OnClickListener) : RecyclerView.Adapter<ViewHolder>() {

    private var items : ArrayList<HolyDay> = arrayListOf();
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder?.holyDayName?.text = items.get(position).holyDayName
        val item = items[position]
        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

     internal fun setWords(holyDayList : ArrayList<HolyDay>) {
        items = holyDayList;
        notifyDataSetChanged()
    }
}


class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val holyDayName = view.tv_holyDayName
}
