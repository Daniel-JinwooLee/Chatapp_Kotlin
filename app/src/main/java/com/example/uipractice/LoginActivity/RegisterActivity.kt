package com.example.uipractice

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.LoginActivity.DashBoardActivity
import com.example.uipractice.LoginActivity.LoginActivity
import com.example.uipractice.UserInfoSetActivity.UserNicknameSetActivity
import com.example.uipractice.databinding.ActivityRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*


class RegisterActivity :AppCompatActivity(){

    companion object{
        private const val RC_SIGN_IN =120
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.registerButton.setOnClickListener {
            performRegister()

        }
        binding.alreadyHaveAnAccountText.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()


        binding.googleLoginButton.setOnClickListener {
            signIn()
        }
        binding.selectPhotoButton.setOnClickListener {
            Log.d("RegisterActivity", "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type ="image/*"
            startActivityForResult(intent, 0)
        }

    }

    private fun performRegister() {
        var id = binding.registerId.text.toString()
        var password = binding.registerPassword.text.toString()

        if(id.isEmpty()||password.isEmpty()) {
            Toast.makeText(this, "이메일 또는 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }


        Log.d("RegisterActivity", "id is : " + id)
        Log.d("RegisterActivity", "Password is : " + password)

        //Firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(id, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    Log.d("Main", "Failed")

                    return@addOnCompleteListener
                }

                //else if successful
                else {
                    Log.d(
                            "RegisterActivity",
                            "Successfully created user with uid : ${it.result?.user?.uid}"
                    )
                    uploadImageToFirebaseStorage()

                    Toast.makeText(this, "Successfully created user", Toast.LENGTH_SHORT).show()



                }
            }
            .addOnFailureListener{
                Log.d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun uploadImageToFirebaseStorage() {
        Log.d("RegisterActivity", "uploadImageToFirebaseStorage 호출됨")

//        if(selectedPhotoUri == null ) {
//            Log.d("RegisterActivity","selectedPhotoUri is null !!! ")
//            return
//        }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        Log.d("RegisterActivity", "uploadImageToFirebaseStorage 호출됨2")
        saveUserToFirebaseDatabase()

//        ref.putFile(selectedPhotoUri!!)
//                .addOnSuccessListener {
//                Log.d("RegisterActivity","Successfully uploaded image : ${it.metadata?.path}")
//
//                ref.downloadUrl.addOnSuccessListener {
//                    it.toString()
//                    Log.d("RegisterActivity","File Location: $it")
//
//                    saveUserToFirebaseDatabase(it.toString())
//                }
//            }
//            .addOnFailureListener {
//                //do some logging here
//                Log.d("RegisterActivity","upload image failed")
//            }

        Log.d("RegisterActivity", "uploadImageToFirebaseStorage 호출됨3")

    }

//    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//
//        val user = UserInfo()
//        user.uid = uid
//        user.username = binding.userText.text.toString()
//
//        ref.setValue(user)
//            .addOnSuccessListener {
//                Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
//                val intent = Intent(this,UserNicknameSetActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
//            }
//    }

//    private fun saveUserToFirebaseDatabase() {
//        val uid = FirebaseAuth.getInstance().uid ?: ""
//        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
//        val user = UserInfo()
//        user.uid = uid
//        user.username = binding.userText.text.toString()
//
//        ref.setValue(user)
//            .addOnSuccessListener {
//                Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
//                val intent = Intent(this,UserNicknameSetActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
////                intent.putExtra()
//                startActivity(intent)
//            }
//
//    }
    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val db = FirebaseFirestore.getInstance()
        val user = UserInfo()
        user.uid = uid
        user.username = binding.userText.text.toString()
        db.collection("users").document(uid).set(user)
                .addOnSuccessListener {
                    Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
                    val intent = Intent(this, UserNicknameSetActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.putExtra()
                    startActivity(intent)
                }


//    setValue(user)
//                .addOnSuccessListener {
//                    Log.d("RegisterActivity","Finally we saved the user to Firebase Database")
//                    val intent = Intent(this,UserNicknameSetActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
////                intent.putExtra()
//                    startActivity(intent)
//                }

    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    var selectedPhotoUri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==0 && resultCode == Activity.RESULT_OK && data != null){
            //proceed and check what the selected image was..
            Log.d("RegisterActivity", "Photo was selected")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            binding.selectPhotoImageView.setImageBitmap(bitmap)
//            val bitmapDrawable =BitmapDrawable(bitmap)
//            binding.selectPhotoButton.setBackgroundDrawable(bitmapDrawable)
            binding.selectPhotoButton.alpha =0f
            Log.d("RegisterActivity", "$selectedPhotoUri")


        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                    // ...
                }
            } else{
                Log.w("SignInActivity", exception.toString())

            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    val intent = Intent(this, DashBoardActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)

                }
            }
    }
}

