package com.example.uipractice.ChatFragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.LoginActivity.LoginActivity
import com.example.uipractice.R
import com.example.uipractice.databinding.FragmentChatPreviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_user_nickname_set.*
import kotlinx.android.synthetic.main.activity_user_nickname_set.view.*
import kotlinx.android.synthetic.main.chat_preview_item.*
import kotlinx.android.synthetic.main.chat_preview_item.view.*
import kotlinx.android.synthetic.main.fragment_chat.*


class FragmentChatPreview : Fragment() {

    companion object{
        val USER_KEY ="USER_KEY"
        var currentUser : User? = null
    }

    val adapter = GroupAdapter<GroupieViewHolder>()
    val TAG = "LatestMessages"
    private lateinit var binding: FragmentChatPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter.setOnItemClickListener { item, view ->
            Log.d(TAG, "123")
            val intent = Intent(activity,ChatRoomActivity::class.java)

            //
            val row = item as LatestMessageRow
            var rowUser = row.chatPartnerUser?.uid?.let { row.chatPartnerUser?.nickname?.let { it1 ->
                row.chatPartnerUser?.profileImageUrl?.let { it2 ->
                    User(it,
                        it1, it2
                    )
                }
            } }


            intent.putExtra(USER_KEY,rowUser)
            startActivity(intent)
        }

        listenForLatestMessages()

        fetchCurrentUser()



    }

    val latestMessagesMap =HashMap<String, ChatMessage>()


    private fun listenForLatestMessages() {
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?:return
                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java) ?:return
                latestMessagesMap[snapshot.key!!] = chatMessage
                refreshRecyclerViewMessages()

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
            }
        })
    }

    private fun refreshRecyclerViewMessages() {
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun fetchCurrentUser() {
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                currentUser= snapshot.getValue(User::class.java)
                Log.d("LatestMessages","Current User ${currentUser?.username}")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_chat_preview,
            container,
            false
        )
        binding.mRecyclerView.addItemDecoration(DividerItemDecoration(activity,DividerItemDecoration.VERTICAL))
        binding.logOutButton.setOnClickListener {
            Log.d("FragmentChatPreview", "온클릭 호출됨")
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        binding.makeNewRoomButton.setOnClickListener {
            Log.d("FragmentChatPreview", "온클릭 호출됨")
            val intent = Intent(activity, NewChatActivity::class.java)
            startActivity(intent)
        }

        binding.mRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.make_room -> {
                val intent = Intent(activity, NewChatActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d("FragmentLatestChat", "onCreateOptionMenu")
        inflater.inflate(R.menu.latest_chat_appbarmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}




//    private fun fetchUsers(){
//        val ref = FirebaseDatabase.getInstance().getReference("/users")
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//
//                snapshot.children.forEach {
//                    Log.d("NewMessage", it.toString())
//                    val user = it.getValue(User::class.java)
//                    if (user != null) {
//                        adapter.add(UserItem(user))
//                    }
//
//                    adapter.setOnItemClickListener { item, view ->
//                        val userItem = item as UserItem
//                        val intent = Intent(view.context, NewChatActivity::class.java)
////                        intent.putExtra(USER_KEY, userItem.user.username)
//                        intent.putExtra(USER_KEY, userItem.user)
//                        startActivity(intent)
//                    }
//                }
//                binding.mRecyclerView.adapter = adapter
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })
//    }

//
//class UserItem (val user: User?) : Item<ChatPreviewItemBinding> (){
//
//    override fun bind(binding: ChatPreviewItemBinding, position: Int) {
//        binding.nicknameText
//    }
//
//    val layout: Int
//        get() = R.layout.chat_preview_item
//
//    override fun getLayout(): Int {
//            TODO("Not yet implemented")
//    }
//}
//
//class UserItem(val user: User)
//    : BindableItem<ChatPreviewItemBinding>() {
//
//    override fun initializeViewBinding(view: View): ViewUserBinding {
//        return ViewUserBinding.bind(view)
//    }
//
//    override fun bind(binding: ViewUserBinding, position: Int) {
//        binding.user = user
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.view_user
//    }
//
//}