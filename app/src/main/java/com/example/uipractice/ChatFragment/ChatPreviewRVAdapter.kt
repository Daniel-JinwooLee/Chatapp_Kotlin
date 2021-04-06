package com.example.uipractice.ChatFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.R

class ChatPreviewRVAdapter(
        private val items: ArrayList<ChatPreviewData>,
        private val listener : OnItemClickListener
): RecyclerView.Adapter<ChatPreviewRVAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.chat_preview_item, parent, false)


        return MyViewHolder(inflatedView).apply {
            itemView.setOnClickListener{
                val curPos : Int = adapterPosition
                val profile : ChatPreviewData = items[curPos]
                Toast.makeText(parent.context,"이름: ${profile.strNickname} 채팅: ${profile.strChatPreviewText} 최근접속시간: ${profile.strRecentChatTime}",
                Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nickname.text = items[position].strNickname
        holder.chatPreviewText.text = items[position].strChatPreviewText
        holder.recentChatTime.text = items[position].strRecentChatTime
        holder.nickname.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //data binding으로 가능?
    inner class MyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val nickname = itemView.findViewById<TextView>(R.id.nickname_text)
        val chatPreviewText = itemView.findViewById<TextView>(R.id.recent_chat_time_chat_preview)
        val recentChatTime = itemView.findViewById<TextView>(R.id.recent_chat_time)


        init{

            itemView.setOnClickListener { this }

        }
        override fun onClick(v: View?){
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(
                    position
                )
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}
