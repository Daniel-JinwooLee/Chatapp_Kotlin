package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.uipractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class IsUserStudentActivity : AppCompatActivity() {
//    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_is_user_student)
        var yesButton : Button = findViewById(R.id.yes_im_a_student_button)
        var noButton : Button = findViewById(R.id.no_im_not_a_student_button)

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("IsUserStudentActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()


        yesButton.setOnClickListener {

            if (currentUserUid != null) {
//                database.child("users").child(currentUserUid).child("job").setValue("학생")

                db.collection("users").document(currentUserUid).update("job", "학생")

            }


            val intent = Intent(this, SchoolSelectActivity ::class.java)
            startActivity(intent)

        }
        noButton.setOnClickListener {

            val intent = Intent(this, JobSetActivity::class.java)
            startActivity(intent)
        }
    }



}