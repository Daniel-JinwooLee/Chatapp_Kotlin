package com.example.uipractice.ChatFragment

import android.os.Parcelable
import com.example.uipractice.R
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid:String , val username :String , val profileImageUrl : Int) : Parcelable{
    constructor() : this("","", R.drawable.user)
}