package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_school_select.*

class SchoolSelectActivity : AppCompatActivity() {
    var text1HaveValue = false
    var text2HaveValue = false
//    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_school_select)

        var schoolNameTextfield : TextView = findViewById(R.id.school_name_textfield)
        var gradeTextField : EditText = findViewById(R.id.grade_textfield)
        var button : Button = findViewById(R.id.school_set_button)


        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("SchoolSelectActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()


        schoolNameTextfield.setOnClickListener {
            val intent = Intent(this, SchoolSearchActivity::class.java)
            startActivity(intent)
        }

        if(intent.hasExtra("school")){
            schoolNameTextfield.text = intent.getStringExtra("school")
            text1HaveValue = true
        }



        school_set_button.setOnClickListener {

            var schoolNameText = schoolNameTextfield.text.toString()
            var schoolGradeText = gradeTextField.text.toString()
            var schoolGrade = schoolGradeText.toInt()
            if (currentUserUid != null) {
//                database.child("users").child(currentUserUid).child("schoolName").setValue(schoolNameText)
//                database.child("users").child(currentUserUid).child("schoolGrade").setValue(schoolGrade)
                db.collection("users").document(currentUserUid).update("schoolName", schoolNameText)
                db.collection("users").document(currentUserUid).update("schoolGrade", schoolGrade)


            }



            val intent = Intent(this, BeforeInterestSelectActivity::class.java)
            startActivity(intent)
        }


        gradeTextField.addTextChangedListener ( object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    Log.d("SchoolSelectActivity" , p0.toString())
                    if(p0.toString().length!=0) {
                        text2HaveValue = true
                        EnableButton()
                    }
                    else{
                        text2HaveValue = false
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

            }
        }
        )

    }
    private fun EnableButton() {

        if(text1HaveValue&&text2HaveValue) {
            school_set_button.isEnabled = true
            Log.d("SchoolSelectActivity", "button enabled")
        }
        else{
            school_set_button.isEnabled = false
            Log.d("SchoolSelectActivity", "button disabled")
        }

    }


}