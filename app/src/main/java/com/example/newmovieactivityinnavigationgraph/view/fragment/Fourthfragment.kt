package com.example.newmovieactivityinnavigationgraph.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newmovieactivityinnavigationgraph.databinding.FragmentFourthfragmentBinding


class Fourthfragment : Fragment() {

    private lateinit var binding: FragmentFourthfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentFourthfragmentBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        getData()

    }



    private fun getData(){

        val args=arguments

        val fTitle=args?.getString("titles")

        binding.title.text=fTitle

        val fImage=args?.getString("images")

        Glide.with(requireContext())

            .load("https://image.tmdb.org/t/p/w342$fImage")

            .apply(RequestOptions.centerCropTransform())

            .into(binding.fourthImage)

        val fPath=args?.getString("path")

        binding.fourthName.text=fPath
    }
}