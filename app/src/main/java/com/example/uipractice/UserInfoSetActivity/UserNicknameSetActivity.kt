package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.uipractice.R
import com.example.uipractice.databinding.ActivityUserNicknameSetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.search_result_item.*

class UserNicknameSetActivity : AppCompatActivity() {
    lateinit var binding : ActivityUserNicknameSetBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_nickname_set)

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("UserNicknameSetActivity", "uid:$currentUserUid")
//        database = Firebase.database.reference
        val db = FirebaseFirestore.getInstance()


        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_nickname_set)

        binding.userNicknameSetButton.setOnClickListener {
            Log.d("UserNicknameSetActivity","button clicked")

            var text:String = binding.nicknameEditText.text.toString()
            if(text.length>=7){
                binding.nicknameWarning.visibility = View.VISIBLE
            }
            else{
                if (currentUserUid != null) {
//                    val map = hashMapOf("nickname" to "$text")
                    db.collection("users").document(currentUserUid).update("nickname", text)

//                    database.child("users").child(currentUserUid).child("nickname").setValue(text)
                }
                val intent = Intent(this, UserGenderSetActivity::class.java)
                startActivity(intent)
            }
        }

        binding.nicknameEditText.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                Log.d("Main" , p0.toString())
                if(p0.toString().length>=2) {
                    binding.userNicknameSetButton.isEnabled = true
                    Log.d("UserNicknameSetActivity","button enabled")
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        )

    }
}