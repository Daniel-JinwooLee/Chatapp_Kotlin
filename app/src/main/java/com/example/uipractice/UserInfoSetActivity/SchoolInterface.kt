package com.example.uipractice.UserInfoSetActivity

import com.example.uipractice.SchoolAPIResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolInterface {
    @GET("getOpenApi")
    fun getSchool(
        @Query("apiKey") apikey : String,
        @Query("svcType") svcType : String,
        @Query("svcCode") svcCode : String,
        @Query("contentType") contentType : String,
        @Query("gubun") gubun : String,
        @Query("searchSchulNm") searchSchulNm : String,
    ): Call<SchoolAPIResult>
}
