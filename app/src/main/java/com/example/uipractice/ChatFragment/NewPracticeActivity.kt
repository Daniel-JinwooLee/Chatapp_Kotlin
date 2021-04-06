package com.example.uipractice.ChatFragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uipractice.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_chat.*

class NewPracticeActivity : AppCompatActivity() {
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_practice)
        putDummys()

        var user = UserInfo()
    }

    private fun putDummys() {
        adapter.add(PracticeItem())
        adapter.add(PracticeItem())
        adapter.add(PracticeItem())
        adapter.add(PracticeItem())

        recycler_view_new_chat_room.adapter = adapter
    }
}


class PracticeItem() : Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.practice_item

    }

}
