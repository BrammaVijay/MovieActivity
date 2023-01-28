package com.example.newmovieactivityinnavigationgraph.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.newmovieactivityinnavigationgraph.R
import com.example.newmovieactivityinnavigationgraph.ViewModelfactory.MovieViewModelFactory
import com.example.newmovieactivityinnavigationgraph.databinding.FragmentThirdBinding
import com.example.newmovieactivityinnavigationgraph.model.Response.Movie
import com.example.newmovieactivityinnavigationgraph.model.network.NetWorkResult
import com.example.newmovieactivityinnavigationgraph.model.repository.MovieRepository
import com.example.newmovieactivityinnavigationgraph.model.retrofit.ResponseApi
import com.example.newmovieactivityinnavigationgraph.vieModel.MovieViewModel
import com.example.newmovieactivityinnavigationgraph.view.Adapter.DataPassAdapter


class ThirdFragment : Fragment() {

    private lateinit var binding: FragmentThirdBinding

    private lateinit var adapter: DataPassAdapter

    private lateinit var viewModel: MovieViewModel

    private var list=ArrayList<Movie>()

    private var secondList=ArrayList<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentThirdBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val interFaces= ResponseApi.retrofit

        val repository= MovieRepository(interFaces)

        list=arguments?.getSerializable("list") as  ArrayList<Movie>

      //  secondList=arguments?.getSerializable("secondList") as ArrayList<Movie>

        viewModel= ViewModelProvider(requireActivity(), MovieViewModelFactory(repository))[MovieViewModel::class.java]

        setAdapter()

        observerData()

        observeDataTwo()

        dataPassInImages()

      //  observerDataThree()

    }

    private fun observerData(){

        viewModel.response.observe(requireActivity(), Observer { response->

            when(response){

                is NetWorkResult.Loading->{

                    Log.d("Loading","Load")
                }

                is NetWorkResult.Success->{

                    response.data?.let { list.addAll(it) }

                    adapter.differ.submitList(list)

                    adapter.notifyDataSetChanged()
                }
                else -> {}
            }
        })
    }

    private fun observeDataTwo(){

        viewModel.responseOne.observe(requireActivity(), Observer { response->

            when(response){

                is NetWorkResult.Loading->{

                }
                is NetWorkResult.Success->{

                    response.data?.let { list.addAll(it) }

                    adapter.differ.submitList(list)

                    adapter.notifyDataSetChanged()
                }
                else -> {}
            }
        })
    }

    private fun dataPassInImages(){

        adapter.imageClickListener { items, boolean ->

         if (boolean){

             val bundle=Bundle()

             bundle.putString("titles",items.title)

             bundle.putString("images",items.posterPath)

             bundle.putString("path",items.overview)

             findNavController().navigate(R.id.action_thirdFragment_to_fourthfragment,bundle)

         }

        }
    }


    private fun observerDataThree(){

        viewModel.responseTwo.observe(requireActivity(), Observer { response->

            when(response){

                is NetWorkResult.Loading->{

                }

                is NetWorkResult.Success->{

                    response.data?.let { list.addAll(it) }

                    adapter.differ.submitList(list)

                    adapter.notifyDataSetChanged()

                }
                else->{

                }
            }
        })
    }

    private fun setAdapter(){

        adapter= DataPassAdapter()

        binding.dataRecyclerView.adapter=adapter

    }
}