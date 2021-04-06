package com.example.uipractice.UserInfoSetActivity

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.R


internal class InterestAdapter() : RecyclerView.Adapter<InterestAdapter.MyViewHolder>() {

    private var items: ArrayList<Interest>? = null
    private var TAG : String = "InterestAdapter"
    interface ItemClickCallback {
        fun onClick(index: Int, item: Interest)
    }

    private var callback: ItemClickCallback? = null


    fun setItemCallback(callback: ItemClickCallback) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.interest_item, parent, false)
        return MyViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = items!![position]
        holder.interestName.text = item.interestName
        holder.interestPopularity.text = item.interestPop.toString()

        val nameColor = if (item.isSelected) {
            Color.parseColor("#80ECFD")
        } else {
            Color.parseColor("#B6B6B6")
        }

        holder.interestName.setBackgroundColor(nameColor)

        holder.interestName.setOnClickListener {
            callback?.onClick(position, item)
        }
    }

    fun updateItem(position: Int, item: Interest) {
        items ?: return

        if (position > items!!.size) {
            return
        }
        this.items!!.removeAt(position)
        this.items!!.add(position, item)
        notifyItemChanged(position)
    }

    fun updateItems(list: List<Interest>) {
        if (this.items == null) {
            items = arrayListOf()
        }
        this.items?.let {
            it.clear()
            it.addAll(list)
            Log.d(TAG, "list : $list")

            notifyDataSetChanged()
        }

    }

    fun getItemList(): ArrayList<Interest>? = items

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val interestName: TextView = itemView.findViewById<TextView>(R.id.interest_name)
        val interestPopularity: TextView = itemView.findViewById<TextView>(R.id.interest_num)


//        val interestNameString : String = interestName.text as String
//        val interestPopInt : Int = interestPopularity.text as Int


    }
}



