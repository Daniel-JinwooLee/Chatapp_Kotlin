package com.example.uipractice.SearchFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.uipractice.R
import com.example.uipractice.databinding.FragmentSearchBinding


class FragmentSearch : Fragment() {

    private lateinit var FSbinding: FragmentSearchBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        FSbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container, false)

        val adapter = VPAdapter(childFragmentManager)
//        adapter.addFragment(FragmentSameSchoolSearch(),"교내검색")
        adapter.addFragment(FragmentNormalSearch(),"일반검색")
        FSbinding.viewpager.adapter = adapter
        FSbinding.tabLayout.setupWithViewPager(FSbinding.viewpager)

        Log.d("debug", "fragment 생성됨")
        Log.d("debug", "fragment 개수 : "+adapter.count.toString())

        return FSbinding.root
    }



}