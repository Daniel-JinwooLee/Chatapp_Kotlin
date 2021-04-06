package com.example.uipractice.ChatFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uipractice.R
import com.example.uipractice.databinding.FragmentChatBinding

class FragmentChat : Fragment(), ChatPreviewRVAdapter.OnItemClickListener{

    private lateinit var FCbinding: FragmentChatBinding
    private val chatPreviewDataList = arrayListOf(
        ChatPreviewData("닉네임A","안녕하세요~","10분전"),
        ChatPreviewData("닉네임B","안녕하세요~","20분전"),
        ChatPreviewData("닉네임C","안녕하세요~","30분전"),
        ChatPreviewData("닉네임D","안녕하세요~","40분전"),
        ChatPreviewData("닉네임E","안녕하세요~","50분전"),
        ChatPreviewData("닉네임F","안녕하세요~","1시간"),
        ChatPreviewData("닉네임G","안녕하세요~","2시간"),
        ChatPreviewData("닉네임H","안녕하세요~","3시간"),
        ChatPreviewData("닉네임I","안녕하세요~","4시간"),
        ChatPreviewData("닉네임J","안녕하세요~","5시간"),
    )
    private val adapter = ChatPreviewRVAdapter(chatPreviewDataList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FCbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat,container, false)

        FCbinding.mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        FCbinding.mRecyclerView.setHasFixedSize(true)

        FCbinding.mRecyclerView.adapter = ChatPreviewRVAdapter(chatPreviewDataList, this)

        return FCbinding.root
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(activity, ChatRoomActivity::class.java)
        startActivity(intent)
    }
}