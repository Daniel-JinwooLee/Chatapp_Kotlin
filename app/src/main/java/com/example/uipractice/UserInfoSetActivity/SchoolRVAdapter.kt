package com.example.uipractice.UserInfoSetActivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.R
import com.example.uipractice.School

class SchoolRVAdapter(private val schoolList: ArrayList<School>,
                      private val listener: OnItemClickListener
                      ) :  RecyclerView.Adapter<SchoolRVAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.school_item, parent, false)
        return MyViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = schoolList[position]

        holder.schoolName.text = schoolList[position].schoolName
        holder.region.text = schoolList[position].region

    }

    override fun getItemCount(): Int {
        return schoolList.size
    }



    inner class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        val schoolName = itemView.findViewById<TextView>(R.id.strSchoolName)
        val region = itemView.findViewById<TextView>(R.id.strRegion)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position : Int)

    }

}