package com.example.uipractice.SearchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uipractice.R
import com.example.uipractice.LinkFragment.SearchResultItemData
import com.example.uipractice.databinding.FragmentSameSchoolSearchBinding

class FragmentSameSchoolSearch : Fragment() {

    private lateinit var binding: FragmentSameSchoolSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_same_school_search,container, false)



        val searchResultData = arrayListOf(
            SearchResultItemData("감자",
                "15분전",
                "안녕하세요~",
                "게임",
                R.drawable.boy2) ,
            SearchResultItemData("시금치",
                "25분전",
                "친해져요",
                "영화",
                R.drawable.girl2)
        )

        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.mRecyclerView.setHasFixedSize(true)

        binding.mRecyclerView.adapter = SearchResultRVAdapter(searchResultData)

        return binding.root
    }

}