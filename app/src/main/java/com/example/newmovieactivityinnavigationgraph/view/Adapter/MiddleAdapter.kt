package com.example.newmovieactivityinnavigationgraph.view.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.newmovieactivityinnavigationgraph.databinding.ListcarditemBinding
import com.example.newmovieactivityinnavigationgraph.model.Response.Movie

class MiddleAdapter:RecyclerView.Adapter<MiddleAdapter.ViewHolderOne>() {

    private var listener:((Movie,Boolean)->Unit)?=null

    fun imageItemClick(clickEvent:(Movie,Boolean)->Unit){

        listener=clickEvent
    }

    inner class ViewHolderOne(private val binding: ListcarditemBinding):RecyclerView.ViewHolder(binding.root){

        fun bindItems(items:Movie){

            Glide.with(itemView.context)

                .load("https://image.tmdb.org/t/p/w342${items.posterPath}")

                .apply(RequestOptions.centerCropTransform())

                .into(binding.itemCardImage)

            binding.cardItem.setOnClickListener {

                listener?.invoke(items,true)
            }
        }
    }

    private var differCallback=object :DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem==newItem
        }
    }

    var differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOne {

        val inflate=LayoutInflater.from(parent.context)

        val binding=ListcarditemBinding.inflate(inflate,parent,false)

        return ViewHolderOne(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderOne, position: Int) {

        holder.bindItems(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}