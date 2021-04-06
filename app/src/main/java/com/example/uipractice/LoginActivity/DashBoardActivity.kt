package com.example.uipractice.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.uipractice.MainActivity
import com.example.uipractice.R
import com.example.uipractice.databinding.ActivityDashBoardBinding
import com.google.firebase.auth.FirebaseAuth

class DashBoardActivity : AppCompatActivity() {

    private lateinit var mAuth : FirebaseAuth
    private lateinit var binding : ActivityDashBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dash_board)
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        binding.uid.text = currentUser?.uid
        binding.userName.text = currentUser?.displayName
        binding.userEmailAdress.text = currentUser?.email
        binding.buttonDashBoard.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}