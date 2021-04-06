package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uipractice.ChatFragment.User
import com.example.uipractice.MainActivity
import com.example.uipractice.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_interest_select.*


class InterestSelectActivity : AppCompatActivity() {

    companion object{
        var numberOfSelectedInterest : Int = 0
    }
    private var mGridLayoutManager: GridLayoutManager? = null
    private var InterestList : MutableList<Interest> = arrayListOf()
//    private lateinit var databaseUser: DatabaseReference


    private var TAG : String = "InterestSelectActivity"

    private var UserInterestList : MutableList<String> = arrayListOf()
    private var NewInterestList : MutableList<String> = arrayListOf()

    private lateinit var adapter: InterestAdapter
    val database = FirebaseDatabase.getInstance().getReference("Interests/")
    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interest_select)

        val currentUser = FirebaseAuth.getInstance()
        val currentUserUid = currentUser.uid
        Log.d("SchoolSelectActivity", "uid:$currentUserUid")
//        databaseUser = Firebase.database.reference

//        val data1 = hashMapOf(
//            "interestName" to "게임",
//            "interestPop" to 5,
//        )
//        val data2 = hashMapOf(
//            "interestName" to "음악",
//            "interestPop" to 3,
//        )
//        val data3 = hashMapOf(
//            "interestName" to "운동",
//            "interestPop" to 2,
//        )
//        val data4 = hashMapOf(
//            "interestName" to "공부",
//            "interestPop" to 1,
//        )
//
//        db.collection("interests").add(data1)
//        db.collection("interests").add(data2)
//        db.collection("interests").add(data3)
//        db.collection("interests").add(data4)



        fetchInterests()


        adapter = InterestAdapter()
        Log.d(TAG, "~~~~~~~~~~~~~~~InterestList : $InterestList")
        adapter.updateItems(InterestList)
        val mRecyclerView : RecyclerView = findViewById(R.id.mRecyclerView)

        mRecyclerView.adapter = adapter

        val numberOfColumns = 2

        mRecyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)

//        val ref = FirebaseDatabase.getInstance().getReference("/Interests")
//
//        ref.setValue(InterestList)
//                .addOnSuccessListener {
//                    Log.d(TAG,"Added InterestList")
//                }





        interest_confirm_button.setOnClickListener {


            if (currentUserUid != null) {
//                databaseUser.child("users").child(currentUserUid).child("interestList").setValue(UserInterestList)
                db.collection("users").document(currentUserUid).update("interestList", UserInterestList)

                    .addOnFailureListener{
                        Log.d("InterestSelectActivity", it.toString())
                    }
                    .addOnSuccessListener {

                    }

                for(interest in InterestList){
                    if(interest.isSelected == true) {
                        db.collection("interests").whereEqualTo("interestName", interest.interestName).get()
                            .addOnSuccessListener { result ->
                                for (document in result) {
                                    Log.d(TAG, "${document.id} => ${document.data}")
                                    db.collection("interests").document(document.id).update("interestPop",interest.interestPop)

                                }
                            }
//                        database.child(interest.interestName).setValue(interest.interestPop)
//                        val data = hashMapOf(
//                           "interestName" to interest.interestName ,  "interestPop" to interest.interestPop
//                        )
//                        db.collection("interests").add(
//                            data
//                        )
// 수정 필요

                    }
                }
                for(newinterest in NewInterestList){
                    val data = hashMapOf(
                        "interestName" to newinterest ,  "interestPop" to 1
                    )
                    db.collection("interests").add(
                        data
                    )
                }
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        adapter.setItemCallback(object : InterestAdapter.ItemClickCallback {
            override fun onClick(index: Int, item: Interest) {

                if (item.isSelected) {
                    numberOfSelectedInterest -= 1
                    item.isSelected = !item.isSelected
                    UserInterestList.remove(item.interestName)
                    item.interestPop = item.interestPop?.minus(1)


                    Log.d(TAG, UserInterestList.toString())
                    Log.d(TAG, numberOfSelectedInterest.toString())
                } else {
                    numberOfSelectedInterest += 1
                    item.isSelected = !item.isSelected
                    UserInterestList.add(item.interestName)
                    item.interestPop = item.interestPop?.plus(1)

                    Log.d(TAG, UserInterestList.toString())
                    Log.d(TAG, numberOfSelectedInterest.toString())

                }

//                var selectedAmount = 0;

//                adapter.getItemList()?.forEach {
//
//                    if (it.isSelected) {
//                        selectedAmount += 1
//                        UserInterestList.add(it)
//                    }
//                }

                if (numberOfSelectedInterest >= 3) {
                    EnableButton()
                } else {
                    DisableButton()
                }

                adapter.updateItem(index, item)
            }
        })

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_new_interest, null)

        val dialogText = dialogView.findViewById<EditText>(R.id.dialogEt)


        add_new_interest_button.setOnClickListener {
            if(dialogView.parent!=null){
                (dialogView.getParent() as ViewGroup).removeView(dialogView)
            }
            builder.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->

                        val text = dialogText.text.toString()

                        if(text.isNotEmpty()) {

                            var alreadyHaveInterest = CheckCurrentInterest(text)
                            if(!alreadyHaveInterest) {

                                val newInterest =
                                        Interest(dialogText.text.toString(), 1, true)
                                UserInterestList.add(newInterest.interestName)
                                NewInterestList.add(newInterest.interestName)
                                InterestList.add(newInterest)
//                                val ref = database.child(newInterest.interestName).setValue(newInterest.interestPop)
//                                                .addOnSuccessListener {
//                                                    Log.d(TAG, "Added Interest to firebase Interests : ${newInterest.interestName} : ${newInterest.interestPop}")
//                                                    numberOfSelectedInterest += 1
//                                                    Log.d(TAG, "numberOfSelectedInterest : $numberOfSelectedInterest, InterestList : $InterestList")
//
//                                                }
                            }

                            adapter.updateItems(InterestList)

                        }


                        /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()
        }


        editTextTextPersonName4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(str: Editable?) {

                if (!str.isNullOrEmpty()) {

                    val resultList = InterestList.filter { it.interestName.contains(str) }
                            .distinct()
                            .toList()

                    if (resultList.size > 0) {
                        adapter.updateItems(resultList)
                    }


                } else {
                    adapter.updateItems(InterestList)
                }


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

    }

    private fun CheckCurrentInterest(text: String): Boolean {
        var alreadyHaveInterest = false
//        var theInterest : Interest
        for (interest in InterestList){
            if(interest.interestName == text){
//                theInterest = interest
                Log.d("CheckCurrentInterest","$interest")

                alreadyHaveInterest = true
                interest.interestPop = interest.interestPop?.plus(1)
                Log.d("CheckCurrentInterest","$interest")
//                database.child(interest.interestName).setValue(interest.interestPop)
                val data = hashMapOf(
                    interest.interestName to interest.interestPop,
                )
                db.collection("interests").add(data)

                Log.d("CheckCurrentInterest", "Added Interest to firebase Interests : ${interest.interestName} : ${interest.interestPop}")


            }
        }

        Log.d("CheckCurrentInterest","$alreadyHaveInterest")


        return alreadyHaveInterest
    }

    private fun fetchInterests() {

        db.collection("interests").orderBy("interestPop", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { result ->
                InterestList.clear()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    var data  = document.data
                    if(data["interestName"]!=null && data["interestPop"] !=null) {
                        var stringValue = data["interestPop"].toString()
                        var integerValue = Integer.parseInt(stringValue)
                        val interest = Interest(
                            interestName = data["interestName"] as String,
                            interestPop = integerValue
                        )
                        InterestList.add(interest)
                    }
                }

                adapter.updateItems(InterestList)
//                for (item in result.get()) {
//                    if (item["InterestName"] !== null && item["InterestPop"] !== null)
//                    val interest = Interest(
//                        interestName = item["InterestName"] as String,
//                        interestPop = item["InterestPop"] as Int
//                    )
//                    InterestList.add(interest)
//                }
                // 가져온 문서들은 result에 들어감
                //                    Log.d(TAG, "item : $item")
                //                    Log.d(TAG, "key : ${item.key} , value : ${item.value}")
                //                    if (item.key !== null && item.value !== null) {
                //                        var stringValue = item.value.toString()
                //                        var integerValue = Integer.parseInt(stringValue)
                //                        interest = Interest(interestName = item.key!!, interestPop = integerValue)
                //                        InterestList.add(interest)
                //                    }
            }
            .addOnFailureListener {
                Log.d("FetchInterests()", "onFailure 호출됨")

            }
    }


//        database.orderByValue().addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                var interest: Interest
//
//                InterestList.clear()
//                for (item in dataSnapshot.children) {
//                    Log.d(TAG, "item : $item")
//                    Log.d(TAG, "key : ${item.key} , value : ${item.value}")
//                    if (item.key !== null && item.value !== null) {
//                        var stringValue = item.value.toString()
//                        var integerValue = Integer.parseInt(stringValue)
//                        interest = Interest(interestName = item.key!!, interestPop = integerValue)
//                        InterestList.add(interest)
//                    }
//
//
////                    val interest : Interest
////                    interest = item.getValue(Interest :: class.java)!!
////                    item.getValue(Interest :: class.java)?.let { InterestList.add(it) }
//
//                    Log.d(TAG, "InterestList : $InterestList")
//
//                }
//
//                adapter.updateItems(InterestList)
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled 호출됨")
//
//            }
//
//        }
//
//        )



//    private fun getInterestList() {
//        var database = FirebaseDatabase.getInstance().getReference("Interests")
//        database.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (item in dataSnapshot.children) {
//                    item.getValue(Interest :: class.java)?.let { InterestList.add(it) }
//                    Log.d(TAG, "item : $item")
//                    Log.d(TAG, "InterestList : $InterestList")
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.d(TAG, "onCancelled 호출됨")
//
//            }
//
//        }
//
//        )
//    }

    fun EnableButton(){
        interest_confirm_button.isEnabled = true
    }

    fun DisableButton(){
        interest_confirm_button.isEnabled = false
    }



}


