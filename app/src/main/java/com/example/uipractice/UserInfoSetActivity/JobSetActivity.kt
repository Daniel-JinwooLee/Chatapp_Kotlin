package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.uipractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class JobSetActivity : AppCompatActivity() {
//    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_set)

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("JobSetActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()

        var button : Button = findViewById(R.id.job_set_button)
        var text : EditText = findViewById(R.id.job_set_text)



        button.setOnClickListener {
            var jobText = text.text.toString()
            if (currentUserUid != null) {
                if(jobText.isEmpty()) {
//                    database.child("users").child(currentUserUid).child("job").setValue("무직")
                    db.collection("users").document(currentUserUid).update("job", "무직")

                }
                else{
//                    database.child("users").child(currentUserUid).child("job").setValue(jobText)
                    db.collection("users").document(currentUserUid).update("job", jobText)

                }
            }

            val intent = Intent(this, BeforeInterestSelectActivity::class.java)
            startActivity(intent)
        }



    }
}