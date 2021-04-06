package com.example.uipractice.UserFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.R
import com.example.uipractice.UserFragment.UserInfoChange.UserInfoChangeActivity
import com.example.uipractice.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.search_result_item.view.*
import java.util.*

class FragmentUser : Fragment() {
    private lateinit var binding: FragmentUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user,container, false)

        val user = FirebaseAuth.getInstance().currentUser

        val db = FirebaseFirestore.getInstance()
        if (user != null) {
            db.collection("users").document(user.uid).get()
                .addOnSuccessListener {

                    var userData = it.toObject(UserInfo::class.java)
                    var imageView = binding.userProfilePic

                    if (userData != null) {
                        when (userData.profileImageUrl) {
                            11 -> {
                                imageView.setImageResource(R.drawable.boy)
                            }
                            12 -> {
                                imageView.setImageResource(R.drawable.boy2)
                            }
                            13 -> {
                                imageView.setImageResource(R.drawable.boy3)
                            }
                            21 -> {
                                imageView.setImageResource(R.drawable.girl)
                            }
                            22 -> {
                                imageView.setImageResource(R.drawable.girl2)
                            }
                            23 -> {
                                imageView.setImageResource(R.drawable.girl3)
                            }
                            null -> {
                                imageView.setImageResource(R.drawable.user)
                            }
                        }
                        binding.userNickname.text = userData.nickname
                        var currentYear = Calendar.getInstance().get(Calendar.YEAR)
                        var userAge = currentYear - userData.birthday?.year!! +1

                        binding.userAgeUserFragment.text = userAge.toString()
                        if(userData.gender == 1){
                            binding.userGender.setImageResource(R.drawable.male)
                        }
                        else{
                            binding.userGender.setImageResource(R.drawable.female)
                        }
//                        binding.userRegion.text = userData.

                        binding.button.setOnClickListener {
                            val intent = Intent(activity, UserInfoChangeActivity::class.java)
                            intent.putExtra("currentUser",userData)
                            startActivity(intent)
                        }


                    }



                }
        }








        return binding.root
    }

}