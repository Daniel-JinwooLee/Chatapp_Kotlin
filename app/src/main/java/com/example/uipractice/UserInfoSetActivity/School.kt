package com.example.uipractice

data class School(
    val link : String ,
    val adres : String,
    val schoolName : String,
    val region :String ,
    val totalCount : Int,
    val estType : String,
    val seq : Int,
)

data class DataSearch(
    val content : List<School>
)

data class SchoolAPIResult(
    val dataSearch: DataSearch
)


