package com.example.uipractice

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.databinding.DataBindingUtil.setContentView

class LoadingDialog
    constructor(context: Context) : Dialog(context) {

    init {
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.loading_dialog)
    }
}