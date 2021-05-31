package com.test.tvmaze.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.databinding.ItemEpisodeBinding
import java.text.SimpleDateFormat

class EpisodeAdapter(private val listener: OnSelectEpisode) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private val itemList: ArrayList<Episode> = arrayListOf()

    interface OnSelectEpisode {
        fun onEpisodeSelected(episode: Episode)
    }

    fun addItems(items: List<Episode>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }

    class EpisodeViewHolder(val itembinding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(itembinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {

        return EpisodeViewHolder(
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = itemList[position]
        with(holder.itembinding) {
            tvName.text = episode.name
            tvSeason.text = "Season ${episode.season}"
            epNumber.text = "#%02d".format(episode.number)
            airtime.text = SimpleDateFormat("dd/MM/yyyy").format(episode.airdate)
            if (episode.number == 1) {
                tvSeason.visibility = View.VISIBLE
            } else {
                tvSeason.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = itemList.size
}