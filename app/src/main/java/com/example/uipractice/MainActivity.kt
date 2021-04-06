package com.example.uipractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.example.uipractice.ChatFragment.FragmentChatPreview
import com.example.uipractice.LinkFragment.FragmentLink
import com.example.uipractice.SearchFragment.FragmentSearch
import com.example.uipractice.UserFragment.FragmentUser
import com.example.uipractice.UserFragment.FragmentUser2
import com.example.uipractice.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //FSbinding = DataBindingUtil.setContentView(this, R.layout.fragment_search)

        verifyUserIsLoggedIn() // 로그인 되있지 않으면 RegisterActivity로 이동

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        val FragmentLatestChat = FragmentChatPreview()
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FragmentLatestChat)
            .commit()
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        else {
//            database = Firebase.database.reference
//            database.child("users").child(uid).child("recentVisitTime").setValue(System.currentTimeMillis()/1000)

            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).update("recentVisitTime", System.currentTimeMillis()/1000)

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.chatItem -> {
                val FragmentLatestChat = FragmentChatPreview()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FragmentLatestChat)
                    .commit()
            }
            R.id.linkItem -> {
                val FragmentLink = FragmentLink()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FragmentLink)
                    .commit()
            }
            R.id.searchItem -> {
                val FragmentSearch = FragmentSearch()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FragmentSearch)
                    .commit()
            }
            R.id.userItem -> {
                val FragmentUser = FragmentUser()
                supportFragmentManager.beginTransaction().replace(R.id.frameLayout, FragmentUser)
                    .commit()
            }
        }
        return true
    }


}


