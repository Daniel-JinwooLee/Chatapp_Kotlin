package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.uipractice.ChatFragment.Date
import com.example.uipractice.R
import com.example.uipractice.UserFragment.UserInfoChange.BasicInfoChange
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.*


class UserBirthdaySetActivity : AppCompatActivity() {
    var isEditing : Boolean = false

//    private lateinit var database: DatabaseReference
    @RequiresApi(Build.VERSION_CODES.O)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_birthday_set)

        isEditing = intent.getBooleanExtra("isEditing", false)

        var button : Button = findViewById(R.id.date_confirm_button)
        var datePicker : DatePicker = findViewById(R.id.vDatePicker)
        datePicker.updateDate(2002, 3, 25);

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("UserBirthdaySetActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
          val db = FirebaseFirestore.getInstance()

        button.setOnClickListener {

            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            var date : Date = Date(day,month,year)
            if(isEditing){
//                val intent = Intent(this, BasicInfoChange::class.java)
                intent.putExtra("day",day)
                intent.putExtra("month", month)
                intent.putExtra("year",year)
                onBackPressed()

//                startActivity(intent)
            }
            else {
                if (currentUserUid != null) {
//                database.child("users").child(currentUserUid).child("birthday").setValue(date)
                    db.collection("users").document(currentUserUid).update("birthday", date)

                }

                val intent = Intent(this, IsUserStudentActivity::class.java)
                startActivity(intent)
            }
        }
        datePicker.setOnDateChangedListener { datePicker, i, i2, i3 ->
            button.isEnabled = true
        }

    }
}