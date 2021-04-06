package com.example.uipractice.SearchFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.LinkFragment.SearchResultItemData
import com.example.uipractice.R

class SearchResultRVAdapter(private val items : ArrayList<SearchResultItemData>): RecyclerView.Adapter<SearchResultRVAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)

        return MyViewHolder(inflatedView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nickname.text = items[position].strNickname
        holder.recentVisitTime.text = items[position].strRecentVisitTime
        holder.shortBio.text = items[position].strShortBio
//        holder.commonInterests.text = items[position].strCommonInterest
        if (items[position].strProfilePicture != 0) {
            holder.profilePic?.setImageResource(items[position].strProfilePicture)
        } else {
            holder.profilePic?.setImageResource(R.mipmap.ic_launcher)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val nickname = itemView.findViewById<TextView>(R.id.nickname_text)
        val recentVisitTime = itemView.findViewById<TextView>(R.id.recent_chat_time_chat_preview)
        val shortBio = itemView.findViewById<TextView>(R.id.short_bio)
//        val commonInterests = itemView.findViewById<TextView>(R.id.common_interests)
        val profilePic = itemView.findViewById<ImageView>(R.id.profilePic_search_result)
    }


}
