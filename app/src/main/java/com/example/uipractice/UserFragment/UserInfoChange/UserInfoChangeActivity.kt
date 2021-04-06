package com.example.uipractice.UserFragment.UserInfoChange

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.LoadingDialog
import com.example.uipractice.MainActivity
import com.example.uipractice.R
import com.example.uipractice.UserInfoSetActivity.IsUserStudentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_info_change.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class UserInfoChangeActivity : AppCompatActivity() {
    var currentUser: UserInfo? = null
    val db = FirebaseFirestore.getInstance()
    val currentUserUid = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_change)


        currentUser = intent.getParcelableExtra<UserInfo>("currentUser")


        showUserData()


        basic_info_change_layout.setOnClickListener {
            val intent = Intent(this, BasicInfoChange::class.java)
            intent.putExtra("currentUser",currentUser)
            startActivity(intent)
        }


    }


    private fun showUserData() {
        if (currentUser != null) {
            Log.d("UserInfoChangeActivity", currentUser!!.nickname.toString())

            when (currentUser!!.profileImageUrl) {
                11 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.boy)
                }
                12 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.boy2)
                }
                13 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.boy3)
                }
                21 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.girl)
                }
                22 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.girl2)
                }
                23 -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.girl3)
                }
                null -> {
                    profile_pic_user_info_change.setImageResource(R.drawable.user)
                }
            }
            user_nickname_profile_edit.text = currentUser!!.nickname
            var thisYear = Calendar.getInstance().get(Calendar.YEAR)
            var userAge = thisYear - currentUser!!.birthday?.year!! + 1
            user_age_profile_edit.text = userAge.toString()
            if (currentUser!!.gender == 1) {
                user_gender_profile_edit.setImageResource(R.drawable.male)
            } else {
                user_gender_profile_edit.setImageResource(R.drawable.male)
            }
            if (currentUser!!.shortBio == null) {
                short_bio_user_info_change.text = ""
            } else {
                short_bio_user_info_change.text = currentUser!!.shortBio
            }
            if (currentUser!!.interestList == null) {

            } else {
                for (text in currentUser!!.interestList!!) {

                    val textView: TextView = buildLabel(text, this)

                    interests_user_info_change.addView(textView)
                }
            }

            if (currentUser!!.job != null) {
                plus_icon_set_job_user_info_change.visibility = View.GONE
                set_job_text_user_info_change.text = currentUser!!.job
            }
            if (currentUser!!.schoolName != null) {
                plus_icon_set_school_user_info_change.visibility = View.GONE
                set_school_text_user_info_change.text = currentUser!!.schoolName
            }
            if (currentUser!!.major != null) {
                plus_icon_set_major_user_info_change.visibility = View.GONE
                set_major_text_user_info_change.text = currentUser!!.major
            }
            if (currentUser!!.region != null) {
                plus_icon_set_region_user_info_change.visibility = View.GONE
                set_region_text_user_info_change.text = currentUser!!.region
            }

            if (currentUser!!.characteristicList == null) {

            } else {
                for (text in currentUser!!.characteristicList!!) {

                    val textView: TextView = buildLabel(text, this)

                    characteristics_user_info_change.addView(textView)
                }
            }

//                else {
//                    for (text in currentUser!!.wantThisKindList!!) {
//
//                        val textView: TextView = buildLabel(text, this)
//
//                        wantThisKindList_user_info_change.addView(textView)
//                    }
//                }
        }
        else{
            Log.d("UserInfoChangeActivity","currentUser is null")

        }
    }

    private fun buildLabel(text: String, context: Context?): TextView {
        val textView = TextView(context)
        textView.text = text
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        textView.setPadding(16, 10, 16, 10)
        textView.setBackgroundResource(R.drawable.light_blue_round_corner_filled)
        textView.setTextColor(Color.WHITE)


        return textView
    }

    private fun showLoadingDialogFor2Sec() {
        val dialog = LoadingDialog(this)
        CoroutineScope(Main).launch {
            dialog.show()
            delay(2000)
            dialog.dismiss()
        }
    }

    private fun showLoadingDialog(dialog : LoadingDialog) {
        dialog.show()
    }

    private fun hideLoadingDialog(dialog : LoadingDialog) {
        dialog.dismiss()
    }

    override fun onBackPressed() {
        val dialog = LoadingDialog(this)

        showLoadingDialog(dialog)

        if (currentUserUid != null && currentUser !=null) {
            db.collection("users").document(currentUserUid).set(currentUser!!).addOnSuccessListener {
                hideLoadingDialog(dialog)
            }
        }

//        showLoadingDialogFor2Sec()


        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    override fun onResume() {

        super.onResume()

        showLoadingDialogFor2Sec()
        currentUser = intent.getParcelableExtra<UserInfo>("currentUser")
        showUserData()

    }

}


//        val db = FirebaseFirestore.getInstance()
//        val user = FirebaseAuth.getInstance().currentUser
//
//        if (user != null) {
//            db.collection("users").document(user.uid).get()
//                .addOnSuccessListener {
//                    currentUser = it.toObject(UserInfo::class.java)
//                }
//        }
//        else{
//            Log.d("UserInfoChangeActivity","user is null")
//        }