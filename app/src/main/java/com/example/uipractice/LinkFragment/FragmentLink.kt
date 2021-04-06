package com.example.uipractice.LinkFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.uipractice.R
import com.example.uipractice.databinding.FragmentLinkBinding

class FragmentLink : Fragment() {

    lateinit var binding : FragmentLinkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_link,container, false)

        var linkProfileData : LinkProfileData =
                LinkProfileData(R.drawable.boy3, "수박이","22","남","서울시","11km",
                        "안녕하세요~ 친구를 찾고 있습니다","강아지, 소설, 웹툰, 음식", "ENSP, 호기심 많은, 수다스러운")
        binding.linkProfilePic.setImageResource(linkProfileData.profilePicture)
        binding.linkProfileNickname.text = linkProfileData.strNickname
        binding.linkProfileAge.text = linkProfileData.strAge
        binding.linkProfileGender.text = linkProfileData.strGender
        binding.linkProfileRegion.text = linkProfileData.strRegion
        binding.linkProfileDistance.text = linkProfileData.strDistance
        binding.linkProfileCharacteristics.text = linkProfileData.strCharacteristics
        binding.linkProfileCommonInterest.text = linkProfileData.strCommonInterest
        binding.linkProfileShortBio.text = linkProfileData.strShortBio

        // 간편화 하는 방법 없을까요?

        return binding.root
    }


}