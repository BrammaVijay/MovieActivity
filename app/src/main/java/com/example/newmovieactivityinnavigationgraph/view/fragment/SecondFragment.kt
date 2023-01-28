package com.example.newmovieactivityinnavigationgraph.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newmovieactivityinnavigationgraph.R
import com.example.newmovieactivityinnavigationgraph.databinding.FragmentFirstBinding
import com.example.newmovieactivityinnavigationgraph.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentSecondBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        firstPopularItems()

       // secondPopularItems()

       // thirdUpItemPass()
    }

    private fun firstPopularItems(){

        val args=arguments

        val title=args?.getString("title")

        binding.movieTitle.text=title

        val overView=args?.getString("overView")

        binding.overViewText.text=overView

        val image=args?.getString("image")
        Glide.with(this)

            .load("https://image.tmdb.org/t/p/w342${image}")

            .apply(RequestOptions.centerCropTransform())

            .into(binding.imageView)
    }

    private fun thirdUpItemPass(){

        val args=arguments

        val upTitle=args?.getString("titles")

        binding.movieTitle.text=upTitle

        val upImages=args?.getString("images")

        val overView=args?.getString("path")

        binding.overViewText.text=overView

        Glide.with(this)

            .load("https://image.tmdb.org/t/p/w342${upImages}")

            .apply(RequestOptions.centerCropTransform())

            .into(binding.imageView)

    }

}