package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.R
import com.example.uipractice.UserFragment.UserInfoChange.BasicInfoChange
import com.example.uipractice.databinding.ActivityUserGenderSetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class UserGenderSetActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserGenderSetBinding
    var isEditing : Boolean = false
    var maleSelected : Boolean = false
    var femaleSelected : Boolean = false
//    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_gender_set)
        Log.d("UserGenderSetActivity","onCreate() 생성됨")

        isEditing = intent.getBooleanExtra("isEditing", false)

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("UserGenderSetActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()


        binding.genderMaleText.setOnClickListener {
            maleSelected = !maleSelected
            if(femaleSelected){
                femaleSelected = false
            }

            changeColor()
        }
        binding.genderFemaleText.setOnClickListener {
            femaleSelected = !femaleSelected
            if(maleSelected){
                maleSelected = false
            }

            changeColor()
        }
        binding.genderConfirmButton.setOnClickListener {

            Log.d("UserGenderSetActivity","isEditing : "+isEditing.toString())

            if(isEditing){
//                val intent = Intent(this, BasicInfoChange::class.java)
//                intent.putExtra("gender",1)
//                startActivity(intent)
                if(maleSelected){
                    intent.putExtra("gender",1)
                }
                else{
                    intent.putExtra("gender",2)
                }
                onBackPressed()
            }
            else{
                if(maleSelected){
                    if (currentUserUid != null) {
//                    database.child("users").child(currentUserUid).child("gender").setValue(1)
                        db.collection("users").document(currentUserUid).update("gender", 1)
                        db.collection("users").document(currentUserUid).update("profileImageUrl", 11)


                    }
                }
                else{
                    if (currentUserUid != null) {
                        db.collection("users").document(currentUserUid).update("gender", 2)
                        db.collection("users").document(currentUserUid).update("profileImageUrl", 21)


                    }
                }
                val intent = Intent(this, UserBirthdaySetActivity::class.java)
                startActivity(intent)
            }


        }

    }


    private fun changeColor(){
        if(maleSelected) {
            binding.genderMaleText.setTextColor(Color.parseColor("#FF03DAC5"))
        }
        else{
            binding.genderMaleText.setTextColor(Color.parseColor("#FF8F8F8F"))
        }
        if(femaleSelected) {
            binding.genderFemaleText.setTextColor(Color.parseColor("#FF03DAC5"))
        }
        else{
            binding.genderFemaleText.setTextColor(Color.parseColor("#FF8F8F8F"))
        }
        binding.genderConfirmButton.isEnabled = maleSelected or femaleSelected
    }
}