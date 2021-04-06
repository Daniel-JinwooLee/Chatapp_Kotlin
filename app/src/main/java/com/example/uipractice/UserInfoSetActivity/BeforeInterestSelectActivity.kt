package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uipractice.R
import kotlinx.android.synthetic.main.activity_before_interest_select.*

class BeforeInterestSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_interest_select)

        go_to_interest_select_button.setOnClickListener {
            val intent = Intent(this, InterestSelectActivity ::class.java)
            startActivity(intent)
        }

    }
}