package com.example.uipractice.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.uipractice.R
import com.example.uipractice.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        mAuth = FirebaseAuth.getInstance()
        var user = mAuth.currentUser
        Handler().postDelayed({
            if(user !=null){
                val dashboardIntent = Intent(this, DashBoardActivity :: class.java)
                startActivity(dashboardIntent)
                finish()
            }
            else{
                val registerIntent = Intent(this, RegisterActivity::class.java)
                startActivity(registerIntent)
                finish()
            }

        },2000)
    }
}