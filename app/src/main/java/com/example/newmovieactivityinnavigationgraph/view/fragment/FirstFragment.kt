package com.example.newmovieactivityinnavigationgraph.view.fragment

import android.content.Intent
import android.graphics.Path
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newmovieactivityinnavigationgraph.R
import com.example.newmovieactivityinnavigationgraph.ViewModelfactory.MovieViewModelFactory
import com.example.newmovieactivityinnavigationgraph.databinding.FragmentFirstBinding
import com.example.newmovieactivityinnavigationgraph.model.Response.Movie
import com.example.newmovieactivityinnavigationgraph.model.network.NetWorkResult
import com.example.newmovieactivityinnavigationgraph.model.repository.MovieRepository
import com.example.newmovieactivityinnavigationgraph.model.retrofit.ResponseApi
import com.example.newmovieactivityinnavigationgraph.vieModel.MovieViewModel
import com.example.newmovieactivityinnavigationgraph.view.Adapter.BottomAdapter
import com.example.newmovieactivityinnavigationgraph.view.Adapter.MiddleAdapter
import com.example.newmovieactivityinnavigationgraph.view.Adapter.TopMovieAdapter
import timber.log.Timber

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private lateinit var topMovieAdapter: TopMovieAdapter

    private lateinit var middleAdapter: MiddleAdapter

    private lateinit var bottomAdapter: BottomAdapter

    private lateinit var viewModel: MovieViewModel

    private lateinit var firstLayoutManager: RecyclerView.LayoutManager

    private lateinit var secondLayoutManager: RecyclerView.LayoutManager

    private lateinit var thirdLayoutManager: RecyclerView.LayoutManager

    private var popularPage = 1

    private var middlePage = 2

    private var bottomPage = 3

    private var isFirstPage = false

    private var isSecondPage = false

    private var isThirdPage = false

    private val list = ArrayList<Movie>()

    private val secondList = ArrayList<Movie>()

    private val thirdList = ArrayList<Movie>()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentFirstBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val interFaces = ResponseApi.retrofit

        val repository = MovieRepository(interFaces)

        viewModel = ViewModelProvider(  requireActivity(), MovieViewModelFactory(repository))[MovieViewModel::class.java]

        setTopAdapter()

        setMiddleAdapter()

        setInBottomAdapter()

        viewModel.getTopMovie()

        viewModel.getPopularMovie()

        viewModel.getApiInBottom()

        setOnFirstObserver()

        popularSetOnObserver()

        bottomSetInObserver()

        topPagination()

        bottomPagination()

        bottomPaginationTwo()

        imageClickingItem()

        imageClickItemTwo()

        imageClickItemThree()

        passTheListItem()

        passTheListItemTwo()


    }

    private fun setOnFirstObserver() {

        viewModel.response.observe(requireActivity(), Observer { response ->

            when (response) {

                is NetWorkResult.Loading -> {

                    binding.progressBar.isVisible = true
                }

                is NetWorkResult.Success -> {

                    binding.progressBar.isVisible = false

                    response.data?.let { list.addAll(it) }

                    topMovieAdapter.differ.submitList(list)    // Call The Api in OnViewCreated Function in call

                    topMovieAdapter.notifyDataSetChanged()

                    isFirstPage = false

                   Timber.tag("movie_list").d(list[0].posterPath)

                }

                is NetWorkResult.Failure -> {

                    binding.progressBar.isVisible = true
                }
                else -> {}
            }
        })
    }

    private fun popularSetOnObserver() {

        viewModel.responseOne.observe(requireActivity(), Observer { response ->

            when (response) {

                is NetWorkResult.Loading -> {

                    binding.progressBar.isVisible = true
                }

                is NetWorkResult.Success -> {

                    binding.progressBar.isVisible = false

                    response.data?.let { secondList.addAll(it) }

                    middleAdapter.differ.submitList(secondList)

                    middleAdapter.notifyDataSetChanged()

                    isSecondPage = false
                }

                is NetWorkResult.Failure -> {

                    binding.progressBar.isVisible = true
                }
                else -> {}
            }
        })
    }

    private fun bottomSetInObserver() {

        viewModel.responseTwo.observe(requireActivity(), Observer { response ->

            when (response) {

                is NetWorkResult.Loading -> {

                    binding.progressBar.isVisible = true

                }

                is NetWorkResult.Success -> {

                    binding.progressBar.isVisible = false

                    response.data?.let { thirdList.addAll(it) }

                    bottomAdapter.differ.submitList(thirdList)

                    bottomAdapter.notifyDataSetChanged()

                    isThirdPage = false

                }
                else -> {}
            }
        })
    }


    private fun imageClickingItem() {

        topMovieAdapter.itemPassClickListener { items, boolean ->

            val bundle = Bundle()

            bundle.putString("image", items.posterPath)

            bundle.putString("title", items.title)

            bundle.putString("overView", items.overview)

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)

        }
    }

    private fun imageClickItemTwo() {

        middleAdapter.imageItemClick { items, boolean ->

            if (boolean) {

                val bundle = Bundle()

                bundle.putString("image", items.posterPath)

                bundle.putString("title", items.title)

                bundle.putString("overView", items.overview)

                findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)

            }
        }
    }

    private fun passTheListItem() {

        binding.popular.setOnClickListener {

            val bundle = Bundle()

            bundle.putSerializable("list", list)

            findNavController().navigate(R.id.action_firstFragment_to_thirdFragment, bundle)
        }
    }

    private fun passTheListItemTwo(){

        binding.topRated.setOnClickListener {

            val bundle=Bundle()

            bundle.putSerializable("list",secondList)

            findNavController().navigate(R.id.action_firstFragment_to_thirdFragment,bundle)

        }
    }

    private fun passTheListItemThree(){

        binding.upComming.setOnClickListener {

            val bundle = Bundle()

            bundle.putSerializable("list", thirdList)

            findNavController().navigate(R.id.action_firstFragment_to_thirdFragment, bundle)

        }
    }

    private fun imageClickItemThree() {

        bottomAdapter.imageItemClickListener { items, boolean ->

            val bundle = Bundle()

            bundle.putString("image", items.posterPath)

            bundle.putString("title", items.title)

            bundle.putString("overView", items.overview)

            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }
    }

    private fun topPagination() {

        binding.firstMovieItemRecyclerVie.addOnScrollListener(object :  RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val childCount = firstLayoutManager.childCount

                val visibleCount = (firstLayoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                val total = topMovieAdapter.itemCount

                if (!isFirstPage) {

                    if ((childCount + visibleCount) == total) {

                        popularPage += 1

                        Toast.makeText(requireContext(), "$popularPage", Toast.LENGTH_SHORT).show()

                        isFirstPage = true

                        viewModel.getTopMovie()

                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun bottomPagination() {

        binding.secondListLRecyclerView.addOnScrollListener(object :  RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val childCount = secondLayoutManager.childCount

                val visibleCount = (secondLayoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                val total = middleAdapter.itemCount

                if (!isSecondPage) {

                    if ((childCount + visibleCount) == total) {

                        middlePage += 1

                        Toast.makeText(requireContext(), "$middlePage", Toast.LENGTH_SHORT).show()

                        isSecondPage = true

                        viewModel.getPopularMovie()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun bottomPaginationTwo() {

        binding.thirdItemRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val childCount = thirdLayoutManager.childCount

                val visibleCount =  (thirdLayoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                val total = bottomAdapter.itemCount

                if (!isThirdPage) {

                    if ((childCount + visibleCount) == total) {

                        bottomPage += 1

                        Toast.makeText(requireContext(), "$bottomPage", Toast.LENGTH_SHORT).show()

                        isThirdPage = true

                        viewModel.getApiInBottom()
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setTopAdapter() {

        topMovieAdapter = TopMovieAdapter()

        binding.firstMovieItemRecyclerVie.adapter = topMovieAdapter

        firstLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.firstMovieItemRecyclerVie.layoutManager = firstLayoutManager
    }

    private fun setMiddleAdapter() {

        middleAdapter = MiddleAdapter()

        binding.secondListLRecyclerView.adapter = middleAdapter

        secondLayoutManager =  LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.secondListLRecyclerView.layoutManager = secondLayoutManager
    }

    private fun setInBottomAdapter() {

        bottomAdapter = BottomAdapter()

        binding.thirdItemRecyclerView.adapter = bottomAdapter

        thirdLayoutManager =   LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.thirdItemRecyclerView.layoutManager = thirdLayoutManager
    }

}