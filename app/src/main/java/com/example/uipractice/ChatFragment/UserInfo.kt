package com.example.uipractice.ChatFragment

import android.os.Parcelable
import com.example.uipractice.R
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
class UserInfo(
    var uid:String? = null,
    var username:String? = null,
    var nickname:String? = null,
    var birthday: Date? = null,
    var age : Int? = null,
    var region : String? = null,
    var gender: Int? = null, //1 for boy 2 for girl
    var schoolName: String? = null,
    var schoolGrade : Int? = null,
    var job : String? = null,
    var profileImageUrl: Int? = 13,
    var recentVisitTime: Long? = null, //??
    var interestList:ArrayList<String>? = null,
    var shortBio: String? = "안녕하세요~",
    var major: String? = null,
    var characteristicList: ArrayList<String>? = null,
    var wantThisKindList: ArrayList<String>? = null,
    var imagesList: ArrayList<String>? = null,
    var noOfCoins: Int? = null,
                //  val region : ?? 위치 거리 등등
                //
 ): Parcelable
{

 }