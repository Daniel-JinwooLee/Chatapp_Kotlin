package com.example.uipractice.ChatFragment

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Date(
    var day: Int? = null,
    var month: Int? = null,
    var year: Int? = null,
    ): Parcelable