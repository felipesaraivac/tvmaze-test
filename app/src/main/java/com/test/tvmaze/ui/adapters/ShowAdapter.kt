package com.test.tvmaze.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.tvmaze.R
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.ui.util.fromHTML

class ShowAdapter(private val listener: OnSelectShow) :
    RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {

    private val itemList: ArrayList<Show> = arrayListOf()

    interface OnSelectShow {
        fun onShowSelected(show: Show)
    }

    fun addItems(items: List<Show>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }

    class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.iv_poster)
        val titleView = itemView.findViewById<TextView>(R.id.tv_name)
        val genreView = itemView.findViewById<TextView>(R.id.tv_genres)
        val descrView = itemView.findViewById<TextView>(R.id.tv_summary)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        return ShowViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_show, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = itemList[position]
        with(holder) {
            titleView.text = show.name.fromHTML()
            genreView.text = show.genres.joinToString { it.uppercase() }
            descrView.text = show.summary.fromHTML()

            Picasso.get()
                .load(show.imageUrl.medium)
                .placeholder(R.drawable.ic_no_picture_24)
                .into(imageView)

            itemView.setOnClickListener {
                listener.onShowSelected(show)
            }
        }
    }

    override fun getItemCount() = itemList.size
}