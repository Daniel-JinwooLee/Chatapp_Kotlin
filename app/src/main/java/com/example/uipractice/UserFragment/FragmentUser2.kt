package com.example.uipractice.UserFragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.example.uipractice.ChatFragment.UserInfo
import com.example.uipractice.R
import com.example.uipractice.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import kotlinx.android.synthetic.main.fragment_user_bubblepicker.*

class FragmentUser2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance()

        db.collection("users").document("${currentUser.uid}").get()
            .addOnSuccessListener {

                val user =  it.toObject(UserInfo::class.java)

                picker.centerImmediately = true
                if (user != null) {
                    Log.d("FragmentUser2", user.interestList.toString())
                    var userInterestList = user.interestList
                    val colors = resources.obtainTypedArray(R.array.colors)


                    if (userInterestList != null) {
                        picker.adapter = object : BubblePickerAdapter {

                            override val totalCount = userInterestList.size

                            override fun getItem(position: Int): PickerItem {
                                return PickerItem().apply {
                                    title = userInterestList[position]
                                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                                    textColor = Color.WHITE
                                }
                            }
                        }


                        picker.listener = object : BubblePickerListener {
                            override fun onBubbleSelected(item: PickerItem) {
                                Toast.makeText(context,"You have clicked"+item.title,
                                    Toast.LENGTH_SHORT)
                            }

                            override fun onBubbleDeselected(item: PickerItem) {

                            }
                        }
                    }

                }

            }


        return inflater.inflate(R.layout.fragment_user_bubblepicker,container,false)
    }




    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }



}