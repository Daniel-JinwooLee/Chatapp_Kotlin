package com.example.uipractice.ChatFragment

import com.example.uipractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_preview_item.view.*
import kotlinx.android.synthetic.main.search_result_item.view.*

class LatestMessageRow(val chatMessage : ChatMessage) : Item<GroupieViewHolder>() {
    var chatPartnerUser: UserInfo? = null
    val db = FirebaseFirestore.getInstance()

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.recent_chat_time_chat_preview.text = chatMessage.text
        val chatPartnerId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
            chatPartnerId = chatMessage.toId
        } else {
            chatPartnerId = chatMessage.fromId
        }
        db.collection("users").document(chatPartnerId).get()
            .addOnSuccessListener {

                chatPartnerUser = it.toObject(UserInfo::class.java)
                viewHolder.itemView.nickname_text_chat_preview.text = chatPartnerUser?.nickname
                if(chatPartnerUser?.recentVisitTime ==null){
                    viewHolder.itemView.recent_chat_time.text = ""
                }
                else {
                    var time = System.currentTimeMillis() / 60000 - chatPartnerUser!!.recentVisitTime!!/60
                    when (time) {
                        in 0..1 -> {
                            viewHolder.itemView.recent_chat_time.text = "방금 전"
                        }
                        in 1..2 -> {
                            viewHolder.itemView.recent_chat_time.text = "1분 전"
                        }
                        in 2..3 -> {
                            viewHolder.itemView.recent_chat_time.text = "2분 전"
                        }
                        in 3..5 -> {
                            viewHolder.itemView.recent_chat_time.text = "3분 전"
                        }
                        in 5..10 -> {
                            viewHolder.itemView.recent_chat_time.text = "5분 전"
                        }
                        in 10..20 -> {
                            viewHolder.itemView.recent_chat_time.text = "10분 전"
                        }
                        in 20..30 -> {
                            viewHolder.itemView.recent_chat_time.text = "20분 전"
                        }
                        in 30..40 -> {
                            viewHolder.itemView.recent_chat_time.text = "30분 전"
                        }
                        in 40..50 -> {
                            viewHolder.itemView.recent_chat_time.text = "40분 전"
                        }
                        in 50..60 -> {
                            viewHolder.itemView.recent_chat_time.text = "50분 전"
                        }
                        in 60..120 -> {
                            viewHolder.itemView.recent_chat_time.text = "1시간 전"
                        }
                        in 120..180 -> {
                            viewHolder.itemView.recent_chat_time.text = "2시간 전"
                        }
                        in 180..240 -> {
                            viewHolder.itemView.recent_chat_time.text = "3시간 전"
                        }
                        in 240..300 -> {
                            viewHolder.itemView.recent_chat_time.text = "4시간 전"
                        }
                        in 300..360 -> {
                            viewHolder.itemView.recent_chat_time.text = "5시간 전"
                        }
                        in 360..420 -> {
                            viewHolder.itemView.recent_chat_time.text = "6시간 전"
                        }
                        in 420..480 -> {
                            viewHolder.itemView.recent_chat_time.text = "7시간 전"
                        }
                        in 480..560 -> {
                            viewHolder.itemView.recent_chat_time.text = "8시간 전"
                        }
                        in 560..1440 -> {
                            var sigan: Int = (time / 60).toInt()
                            viewHolder.itemView.recent_chat_time.text = "${sigan}시간 전"
                        }
                        in 1440..2880 -> {
                            viewHolder.itemView.recent_chat_time.text = "하루 전"
                        }
                        in 2880..4320 -> {
                            viewHolder.itemView.recent_chat_time.text = "이틀 전"
                        }
                        in 4320..43200 -> {
                            var day: Int = (time / 1440).toInt()
                            viewHolder.itemView.recent_chat_time.text = "${day}일 전"
                        }
                        in 43200..525600 -> {
                            var day: Int = (time / 43200).toInt()
                            viewHolder.itemView.recent_chat_time.text = "${day}달 전"
                        }
                        else ->{
                            viewHolder.itemView.recent_chat_time.text = "한참 전"
                        }
                    }
                }

                when (chatPartnerUser?.profileImageUrl) {
                    11 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.boy)
                    }
                    12 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.boy2)
                    }
                    13 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.boy3)
                    }
                    21 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.girl)
                    }
                    22 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.girl2)
                    }
                    23 -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.girl3)
                    }
                    null -> {
                        viewHolder.itemView.profilePic_chat_preview.setImageResource(R.drawable.user)
                    }

                }


//        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
////                chatPartnerUser = snapshot.getValue(User::class.java)
//                viewHolder.itemView.nickname_text_chat_preview.text = chatPartnerUser?.username
//                val targetImageView = viewHolder.itemView.profilePic_chat_preview
//                chatPartnerUser?.let { targetImageView.setImageResource(it.profileImageUrl) }
////                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
            }

    }
    override fun getLayout(): Int {
        return R.layout.chat_preview_item
    }
}