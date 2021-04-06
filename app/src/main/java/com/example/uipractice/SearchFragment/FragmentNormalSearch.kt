package com.example.uipractice.SearchFragment


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uipractice.ChatFragment.ChatRoomActivity
import com.example.uipractice.ChatFragment.NewChatActivity
import com.example.uipractice.ChatFragment.User
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.R
import com.example.uipractice.UserInfoSetActivity.Interest
import com.example.uipractice.databinding.FragmentNormalSearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.common_interest_textview.view.*
import kotlinx.android.synthetic.main.fragment_link.view.*
import kotlinx.android.synthetic.main.search_result_item.view.*
import kotlin.coroutines.coroutineContext

class FragmentNormalSearch : Fragment() {


    val adapter = GroupAdapter<GroupieViewHolder>()


    private lateinit var binding: FragmentNormalSearchBinding
    private var mUser : ArrayList<User>? = null
    private var mAdapter : SearchResultRVAdapter? =null
//    private lateinit var database: DatabaseReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_normal_search,
                container,
                false
        )

        getRecentUsers()

//        retrieveAllUsers()

//        val searchResultData = arrayListOf(
//            SearchResultItemData("토마토",
//                "10분전",
//                "안녕하세요 친하게 지냈으면 좋겠습니다!",
//                "운동, 게임, 영화",
//                R.drawable.boy) ,
//            SearchResultItemData("당근이",
//                "15분전",
//                "반가워요",
//                "음악, 게임, 노래",
//                R.drawable.girl)
//        )



        return binding.root
    }

    private fun getRecentUsers() {
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance()


        db.collection("users").orderBy("recentVisitTime",Query.Direction.DESCENDING).get()
            .addOnSuccessListener {documents->
                for(doc in documents){
                    Log.d("Recent visit time:", doc.toString())


                    val user =  doc.toObject(UserInfo::class.java)
                    if(user.uid != currentUser.uid) {
                        Log.d("Recent visit time:", user.recentVisitTime.toString())
                        adapter.add(SearchResultItem(user, context))
                    }


                }
//                adapter.setOnItemClickListener { item, view ->
//
//                    // 클릭시 이벤트
//
//                }
                binding.mRecyclerView.layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                )
                binding.mRecyclerView.adapter = adapter

            }


//        val ref = FirebaseDatabase.getInstance().getReference("/users").orderByChild("recentVisitTime")
//
//        ref.addListenerForSingleValueEvent(object : ValueEventListener {
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                p0.children.forEach {
//                    Log.d("Found User", it.toString())
//                    val user = it.getValue(UserInfo::class.java)
//                    if (user != null) {
//                        Log.d("Recent visit time:", user.recentVisitTime.toString())
//                    }
//                    if (user != null) {
//                        adapter.add(SearchResultItem(user, context))
//                    }
//                }
//
//                adapter.setOnItemClickListener { item, view ->
//
//                    // 클릭시 이벤트
//
//                }
//                binding.mRecyclerView.layoutManager = LinearLayoutManager(
//                        activity,
//                        LinearLayoutManager.VERTICAL,
//                        false
//                )
//                binding.mRecyclerView.adapter = adapter
//
//            }
//
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//        })


//        binding.mRecyclerView.adapter = SearchResultRVAdapter(mostRecentUserQuery.)

    }

    class SearchResultItem(
            val user: UserInfo,
            val context: Context?
    ) : Item<GroupieViewHolder>(){
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
                viewHolder.itemView.nickname_text?.text = user.nickname
            Log.d("SearchResultItem", "${user}")
            Log.d("SearchResultItem", "${user.interestList}")

            if(user.interestList == null){

            }else {
                for (text in user.interestList!!) {

                    val textView: TextView = buildLabel(text, context)

                    viewHolder.itemView.common_interests.addView(textView)
                }
            }
            if(user.recentVisitTime==null){
                viewHolder.itemView.recent_chat_time_search_result.text = ""
            }
            else {
                var time = System.currentTimeMillis() / 60000 - user.recentVisitTime!!/60
                when (time) {
                    in 0..1 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "방금 전"
                    }
                    in 1..2 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "1분 전"
                    }
                    in 2..3 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "2분 전"
                    }
                    in 3..5 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "3분 전"
                    }
                    in 5..10 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "5분 전"
                    }
                    in 10..20 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "10분 전"
                    }
                    in 20..30 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "20분 전"
                    }
                    in 30..40 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "30분 전"
                    }
                    in 40..50 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "40분 전"
                    }
                    in 50..60 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "50분 전"
                    }
                    in 60..120 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "1시간 전"
                    }
                    in 120..180 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "2시간 전"
                    }
                    in 180..240 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "3시간 전"
                    }
                    in 240..300 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "4시간 전"
                    }
                    in 300..360 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "5시간 전"
                    }
                    in 360..420 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "6시간 전"
                    }
                    in 420..480 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "7시간 전"
                    }
                    in 480..560 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "8시간 전"
                    }
                    in 560..1440 -> {
                        var sigan: Int = (time / 60).toInt()
                        viewHolder.itemView.recent_chat_time_search_result.text = "${sigan}시간 전"
                    }
                    in 1440..2880 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "하루 전"
                    }
                    in 2880..4320 -> {
                        viewHolder.itemView.recent_chat_time_search_result.text = "이틀 전"
                    }
                    in 4320..43200 -> {
                        var day: Int = (time / 1440).toInt()
                        viewHolder.itemView.recent_chat_time_search_result.text = "${day}일 전"
                    }
                    in 43200..525600 -> {
                        var day: Int = (time / 43200).toInt()
                        viewHolder.itemView.recent_chat_time_search_result.text = "${day}달 전"
                    }
                    else ->{
                        viewHolder.itemView.recent_chat_time_search_result.text = "한참 전"
                    }
                }
            }

            viewHolder.itemView.short_bio.text = user.shortBio
            Log.d("SearchResultItem", user.profileImageUrl.toString())
                when (user.profileImageUrl){
                    11->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.boy)
                    }
                    12->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.boy2)
                    }
                    13->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.boy3)
                    }
                    21->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.girl)
                    }
                    22->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.girl2)
                    }
                    23->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.girl3)
                    }
                    null->{viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.user)
                    }

                }



//            if(user.profileImageUrl == null) {
//                if (user.gender == 1) {
//                    viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.boy2)
//                } else {
//                    viewHolder.itemView.profilePic_search_result.setImageResource(R.drawable.girl2)
//                }
//            }
//            else{
//                viewHolder.itemView.profilePic_search_result.setImageResource(user.profileImageUrl!!)
//            }

            viewHolder.itemView.make_chat_room_icon.setOnClickListener {
                val intent = Intent(context, ChatRoomActivity::class.java)
//          intent.putExtra(USER_KEY,  userItem.user.username)
                if(user.uid != null && user.nickname !=null && user.profileImageUrl !=null) {

                    var chattingUser = User(user.uid!!, user.nickname!!, user.profileImageUrl!!)
                    intent.putExtra(NewChatActivity.USER_KEY, chattingUser)

//                    if (user.gender == 1) {
//                        var chattingUser = User(user.uid!!, user.nickname!!, R.drawable.boy)
//                        intent.putExtra(NewChatActivity.USER_KEY, chattingUser)
//                    } else {
//                        var chattingUser = User(user.uid!!, user.nickname!!, R.drawable.girl)
//                        intent.putExtra(NewChatActivity.USER_KEY, chattingUser)
//
//                    }
                    context?.startActivity(intent)
                }
                else{
                    Log.d("FragmentNormalSearch","user.uid != null && user.nickname !=null && user.profileImageUrl !=null is not true")
                }

            }







//            holder.recentVisitTime.text = items[position].strRecentVisitTime
//            holder.shortBio.text = items[position].strShortBio
//            holder.commonInterests.text = items[position].strCommonInterest
//            if (items[position].strProfilePicture != 0) {
//                holder.profilePic?.setImageResource(items[position].strProfilePicture)
//            } else {
//                holder.profilePic?.setImageResource(R.mipmap.ic_launcher)
//            }
        }

        private fun buildLabel(text: String, context: Context?): TextView {
            val textView = TextView(context)
            textView.text = text
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.setPadding(16,10,16,10)
            textView.setBackgroundResource(R.drawable.light_blue_round_corner_filled)
            textView.setTextColor(Color.WHITE)


            return textView
        }


        override fun getLayout(): Int {
            return R.layout.search_result_item

        }

    }




//
//    private fun retrieveAllUsers() {
//        val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
//        val refUsers = FirebaseDatabase.getInstance().reference.child("users")
//        database = Firebase.database.reference
//
//        val mostRecentUserQuery = database.child("users")
//                .orderByChild("metrics/views")
//
//
//        refUsers.addValueEventListener(object :ValueEventListener{
//
//            override fun onDataChange(p0: DataSnapshot) {
//
//                p0.children.forEach {
//                    val user = it.getValue(User::class.java)
//                    if (user != null) {
//                        if(!(user.uid.equals(firebaseUserID))){ //자신이 아닌
//
//                            mUser?.add(user)
//                        }
//                    }
//                }
//
//
//            }
//
//
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//
//        })
//
//    }

}