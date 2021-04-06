package com.example.uipractice.ChatFragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.uipractice.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_chat.*
import kotlinx.android.synthetic.main.chat_preview_item.view.*

class NewChatActivity : AppCompatActivity() {
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        supportActionBar?.title = "Select User"

//        putDummys()

        fetchUsers()
    }

//    private fun putDummys() {
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        recycler_view_new_chat_room.adapter = adapter
//    }


    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {

                p0.children.forEach {
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(User::class.java)
                    if (user != null){
                        adapter.add(UserItem(user))
                    }
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem

                    val intent = Intent(view.context, ChatRoomActivity::class.java)
//          intent.putExtra(USER_KEY,  userItem.user.username)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)

                    finish()

                }

                recycler_view_new_chat_room.adapter = adapter

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

}


class UserItem(
    val user: User
    ) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.nickname_text_chat_preview.text = user.username
        viewHolder.itemView.profilePic_chat_preview.setImageResource(user.profileImageUrl)
//        if(user.profileImageUrl.isEmpty()) {

//            val emptyImage= "https://firebasestorage.googleapis.com/v0/b/fir-tuts-ffc82.appspot.com/o/images%2F9614c6f5-0aef-453f-8e3d-4b9f2de693c4?alt=media&token=771b75df-cd0f-47ca-9027-341a93f2984c"
//            Picasso.get().load(emptyImage).into(viewHolder.itemView.profilePic_chat_preview)

//        }
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.profilePic_chat_preview)
    }

    override fun getLayout(): Int {
        return R.layout.chat_preview_item

    }

}
