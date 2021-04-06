package com.example.uipractice.UserInfoSetActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uipractice.*
import com.example.uipractice.databinding.ActivitySchoolSearchBinding
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList


class SchoolSearchActivity : AppCompatActivity() , SchoolRVAdapter.OnItemClickListener{
    lateinit var retrofit: Retrofit
    lateinit var schoolAPI: SchoolInterface
    private lateinit var binding: ActivitySchoolSearchBinding
    val schoolList = ArrayList<School>()
    var schoolRVAdapter: SchoolRVAdapter = SchoolRVAdapter(schoolList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("SchoolSearchActivity","onCreate() 호출됨")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_school_search)
        retrofit = RetrofitClient.getInstance()
        schoolAPI = retrofit.create(SchoolInterface::class.java)

        binding.schoolRecyclerView.adapter = schoolRVAdapter

        binding.schoolSetTextButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_new_interest, null)
            val dialogText = dialogView.findViewById<EditText>(R.id.school_dialog_Et)

            builder.setView(dialogView)
                    .setPositiveButton("확인") { dialogInterface, i ->

                        val intent = Intent(this, SchoolSelectActivity::class.java)
                        intent.putExtra("school",dialogText.toString())
                        startActivity(intent)
                        /* 확인일 때 main의 View의 값에 dialog View에 있는 값을 적용 */

                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }
                    .show()
        }

        binding.schoolSearchButton.setOnClickListener {
            schoolList.clear()
            Log.d("SchoolSearchActivity","setOnClickListener 호출됨")

            var searchWord: String = binding.schoolSearchKeyWord.text.toString()

            schoolAPI.getSchool(
                    "1fea75ab08de6847f87c22effb470b3f",
                    "api",
                    "SCHOOL",
                    "json",
                    "univ_list",
                    searchWord
            ).enqueue(SchoolAPICallback(schoolList, schoolRVAdapter))
            schoolAPI.getSchool(
                    "1fea75ab08de6847f87c22effb470b3f",
                    "api",
                    "SCHOOL",
                    "json",
                    "elem_list",
                    searchWord
            ).enqueue(SchoolAPICallback(schoolList, schoolRVAdapter))
            schoolAPI.getSchool(
                    "1fea75ab08de6847f87c22effb470b3f",
                    "api",
                    "SCHOOL",
                    "json",
                    "midd_list",
                    searchWord
            ).enqueue(SchoolAPICallback(schoolList, schoolRVAdapter))
            schoolAPI.getSchool(
                    "1fea75ab08de6847f87c22effb470b3f",
                    "api",
                    "SCHOOL",
                    "json",
                    "high_list",
                    searchWord
            ).enqueue(SchoolAPICallback(schoolList, schoolRVAdapter))

            Log.d("SchoolSearchActivity", "schoolList 개수 :" + schoolList.size.toString())



        }



    }
    public fun showSchoolSetButton(){
        binding.schoolSetTextButton.visibility = View.VISIBLE
        binding.isSchoolNotShowingText.visibility = View.VISIBLE

    }
    class SchoolAPICallback(var schoolList: ArrayList<School>, var adapter: SchoolRVAdapter) :
        Callback<SchoolAPIResult> {

        override fun onResponse(call: Call<SchoolAPIResult>, response: Response<SchoolAPIResult>) {
            if (response.isSuccessful) {
                var body = response.body()

                if (body !== null) {
                    Log.d("SchoolSearchActivity", "개수 :" + body.dataSearch.content.size.toString())

                    for (school in body.dataSearch.content) {
                        Log.d("SchoolSearchActivity", school.schoolName)
                        Log.d("SchoolSearchActivity", school.region)
                        schoolList.add(
                                School(
                                        schoolName = school.schoolName,
                                        region = school.region,
                                        adres = school.adres,
                                        totalCount = school.totalCount,
                                        link = school.link,
                                        estType = school.estType,
                                        seq = school.seq
                                )
                        )
                    }
                    Log.d(
                            "SchoolSearchActivity",
                            "class SchoolAPICallback 안에 Array<School>개수 :" + schoolList.size.toString()
                    )
                    if(schoolList.size==0){
//                        showSchoolSetButton()
                    }
                    adapter.notifyDataSetChanged()


                }
            }

        }

        override fun onFailure(call: Call<SchoolAPIResult>, t: Throwable) {
            Log.d("Debug", "onFailure 호출됨")
            Log.d("Debug", "$t")

        }

    }

    object RetrofitClient {
        private var instance: Retrofit? = null

        fun getInstance(): Retrofit {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .baseUrl("https://www.career.go.kr/cnet/openapi/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return instance!!
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = schoolList[position]
        val intent = Intent(this, SchoolSelectActivity::class.java)
        intent.putExtra("school",schoolList[position].schoolName)
        startActivity(intent)
    }
}