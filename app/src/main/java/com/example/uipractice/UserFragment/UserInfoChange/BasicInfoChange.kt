package com.example.uipractice.UserFragment.UserInfoChange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.uipractice.ChatFragment.Date
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.LoadingDialog
import com.example.uipractice.R
import com.example.uipractice.UserInfoSetActivity.UserBirthdaySetActivity
import com.example.uipractice.UserInfoSetActivity.UserGenderSetActivity
import kotlinx.android.synthetic.main.activity_basic_info_change.*
import kotlinx.android.synthetic.main.dialog_edit_text.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BasicInfoChange : AppCompatActivity() {
    var currentUser: UserInfo? = null
    var genderFromIntent = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_info_change)

        getUserData()


    }

    private fun getUserData() {
        currentUser = intent.getParcelableExtra<UserInfo>("currentUser")

        nickname_text_basic_info_change.text = currentUser?.nickname
        birthday_text_basic_info_change.text = "${currentUser?.birthday?.year}년 ${currentUser?.birthday?.month}월 ${currentUser?.birthday?.year}일"
        if (currentUser?.gender == 1) {
            gender_text_basic_info_change.text = "남자"
        } else {
            gender_text_basic_info_change.text = "여자"
        }
        //user info text set


        nickname_change_layout_clickable.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_text, null)

            builder.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->
                        /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */
                        if(edit_text_dialog_Et.text !=null) {
                            var text =
                                edit_text_dialog_Et.text.toString()
                            nickname_text_basic_info_change.setText(text)
                            currentUser?.nickname = edit_text_dialog_Et.text.toString()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()
        }

        birthday_change_layout_clickable.setOnClickListener {
            val intent = Intent(this, UserBirthdaySetActivity::class.java)
            intent.putExtra("isEditing",true)
            startActivity(intent)
        }

        gender_change_layout_clickable.setOnClickListener {
            val intent = Intent(this, UserGenderSetActivity::class.java)
            intent.putExtra("isEditing",true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        Log.d("BasicInfoChange", "onResume() 호출됨")
        showLoadingDialogFor2Sec()
        Log.d("BasicInfoChange", "onResume() 호출됨2")


        genderFromIntent = intent.getIntExtra("gender",0)

        if(genderFromIntent != 0) {
            if (genderFromIntent == 1) {
                gender_text_basic_info_change.text = "남자"
            } else {
                gender_text_basic_info_change.text = "여자"
            }
            currentUser?.gender = genderFromIntent
        }

        Log.d(" onResume()", "genderFromIntent : "+genderFromIntent.toString())


        var dayFromIntent  = intent.getIntExtra("day", 0)
        var monthFromIntent  = intent.getIntExtra("month", 0)
        var yearFromIntent  = intent.getIntExtra("year", 0)
        if(dayFromIntent != 0){
            currentUser?.birthday = Date(dayFromIntent,monthFromIntent,yearFromIntent)
            birthday_text_basic_info_change.text = "${currentUser?.birthday?.year}년 ${currentUser?.birthday?.month}월 ${currentUser?.birthday?.year}일"
        }
        Log.d(" onResume()", "Birthday from Intent : "+dayFromIntent.toString()+","+monthFromIntent.toString()+","+yearFromIntent.toString() )



    }



//    override fun onBackPressed() {
//        val intent = Intent(this, UserInfoChangeActivity::class.java)
//        intent.putExtra("currentUser",currentUser)
//        startActivity(intent)
//    }

    override fun onDestroy() {
        intent.putExtra("currentUser",currentUser)
        super.onDestroy()
    }
    private fun showLoadingDialogFor2Sec() {
        val dialog = LoadingDialog(this)
        CoroutineScope(Dispatchers.Main).launch {
            dialog.show()
            delay(2000)
            dialog.dismiss()
        }
    }

    }