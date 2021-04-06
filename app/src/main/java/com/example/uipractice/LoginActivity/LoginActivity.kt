package com.example.uipractice.LoginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.uipractice.MainActivity
import com.example.uipractice.R
import com.example.uipractice.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__login)


        var LoginButton : Button = findViewById(R.id.login_button)
        var BacktoRegisterTextView : TextView = findViewById(R.id.back_to_register)


        LoginButton.setOnClickListener {

            perfomLogin()

        }
        BacktoRegisterTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun perfomLogin() {
        var IDEditText : EditText = findViewById(R.id.login_id)
        var PWEditText : EditText = findViewById(R.id.login_pw)
        var ID = IDEditText.text.toString()
        var PW = PWEditText.text.toString()
        Log.d("LoginActivity","id is : "+ IDEditText)
        Log.d("LoginActivity","Password is : "+ PWEditText)
        if(ID.isEmpty()||PW.isEmpty()) {
            Toast.makeText(this, "이메일 또는 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(ID,PW)
                .addOnCompleteListener {
                    if(!it.isSuccessful){
                        Log.d("LoginActivity","Failed")

                        return@addOnCompleteListener
                    }

                    else {
                        Log.d(
                                "LoginActivity",
                                "Successfully logged ini : ${it.result?.user?.uid}"
                        )

                        Toast.makeText(this, "Successfully logined user", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java )
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {
                    Log.d("LoginActivity","Failed to login user: ${it.message}")
                    Toast.makeText(this, "Failed to login user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
    }
}