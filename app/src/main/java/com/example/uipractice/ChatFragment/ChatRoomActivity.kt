package com.example.uipractice.ChatFragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.uipractice.R
import com.example.uipractice.databinding.ActivityChatRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatRoomActivity : AppCompatActivity() {

    var chattingUser : User? = null
    var currentUser : User? = null

    companion object{
        val TAG = "Chat Log"
    }
    val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var binding : ActivityChatRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_room)

        binding.recyclerViewChatRoom.adapter =adapter

        setSupportActionBar(binding.toolbarNewChat)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        val username = intent.getStringExtra(FragmentChatPreview.USER_KEY)

        chattingUser = intent.getParcelableExtra<User>(FragmentChatPreview.USER_KEY)

        if (chattingUser != null) {
            supportActionBar?.title = chattingUser!!.username
        }
        fetchCurrentUser()

//        setUpDummyData()




        chat_send_button.setOnClickListener {
            Log.d(TAG,"Attempt to send message....")
            performSendMessage()
        }
    }

    private fun listenForMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = chattingUser?.uid

        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)
                if(chatMessage!=null) {
                    Log.d(TAG,chatMessage.text)
                    if(chatMessage.fromId == chattingUser?.uid) {
                        adapter.add(ChatFromItem(chatMessage.text, chattingUser!!))
                    }
                    //from --> 받는거, 수신

                    else {
                        if(currentUser!=null) {
                            adapter.add(ChatToItem(chatMessage.text, currentUser!!))
                        }
                        else{
                            Log.d("listenForMessages", "current user is null")

                        }
                    }
                    recycler_view_chat_room.scrollToPosition(adapter.itemCount -1)
                    //to --> 주는거, 발신

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }



    private fun performSendMessage() {
        // how?


        val text = chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(FragmentChatPreview.USER_KEY)
        val toId = user?.uid

//        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()


        if(fromId == null ) return

        val chatMessage = toId?.let {
            ChatMessage(reference.key!!, text, fromId,
                it, System.currentTimeMillis()/1000)
        }

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG,"Saved our chat message : ${reference.key}")
                chat_log.text.clear()
                recycler_view_chat_room.scrollToPosition(adapter.itemCount-1)
            }

        toReference.setValue(chatMessage)
                .addOnSuccessListener {
                    Log.d(TAG,"Saved our chat message : ${reference.key}")
                    chat_log.text.clear()
                }

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatMessage)
    }

    private fun setUpDummyData() {
        val adapter = GroupAdapter<GroupieViewHolder>()



        binding.recyclerViewChatRoom.adapter = adapter

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            android.R.id.home-> this.finish()
            R.id.report -> Log.d("ChatRoomActivity","User Reported")
            R.id.block -> Log.d("ChatRoomActivity","User Blocked")
            R.id.delete_all_chat -> Log.d("ChatRoomActivity","Deleted All Chat")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chat_appbarmenu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    private fun fetchCurrentUser() {
        val db = FirebaseFirestore.getInstance()

        val uid = FirebaseAuth.getInstance().uid
        if(uid !=null) {
            val docRef = db.collection("users").document(uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("fetchCurrentUser", "DocumentSnapshot data: ${document.data}")
                        currentUser =  document.toObject(User::class.java)
                        Log.d("fetchCurrentUser", "currentUser $currentUser")

                        listenForMessages()

                    } else {
                        Log.d("fetchCurrentUser", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("fetchCurrentUser", "get failed with ", exception)
                }
        }
        else {
            Log.d("fetchCurrentUser", "uid is null")
        }



    }
}

class ChatFromItem(val text:String, val user : User) : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_from_row.text = text

        when (user.profileImageUrl) {
            11 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.boy)
            }
            12 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.boy2)
            }
            13 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.boy3)
            }
            21 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.girl)
            }
            22 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.girl2)
            }
            23 -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.girl3)
            }
            null -> {
                viewHolder.itemView.profile_image_chat_from_row.setImageResource(R.drawable.user)
            }
        }
//        val targetImageView = viewHolder.itemView.profile_image_chat_to_row
//        targetImageView.setImageResource(user.profileImageUrl)

//        Picasso.get().load(uri).into(targetImageView)

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem (val text:String,
                  val user : User
                  )  : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textView_to_row.text = text

        when (user.profileImageUrl) {
            11 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.boy)
            }
            12 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.boy2)
            }
            13 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.boy3)
            }
            21 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.girl)
            }
            22 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.girl2)
            }
            23 -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.girl3)
            }
            null -> {
                viewHolder.itemView.profile_image_chat_to_row.setImageResource(R.drawable.user)
            }
        }

//        val targetImageView = viewHolder.itemView.profile_image_chat_to_row
//        targetImageView.setImageResource(user.profileImageUrl)

//        Picasso.get().load(uri).into(targetImageView)

    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}