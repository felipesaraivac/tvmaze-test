package com.test.tvmaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.test.tvmaze.R
import com.test.tvmaze.data.model.Episode
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.databinding.ShowsOverviewFragmentBinding
import com.test.tvmaze.ui.adapters.EpisodeAdapter
import com.test.tvmaze.ui.util.fromHTML
import com.test.tvmaze.ui.util.onLoad
import com.test.tvmaze.ui.viewmodel.ShowViewModel
import com.test.tvmaze.ui.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowOverviewFragment() : Fragment(), EpisodeAdapter.OnSelectEpisode {

    private val viewModel by viewModel<ShowViewModel>()

    private val episodeAdapter = EpisodeAdapter(this)
    private var _binding: ShowsOverviewFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance(showId: Int): ShowOverviewFragment {
            val args = Bundle()
            args.putInt("showId", showId)
            val fragment = ShowOverviewFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShowsOverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.shows_overview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(view.findViewById(R.id.toolbar))

        setupViewModels()
        binding.rvEpisodeList.adapter = episodeAdapter
    }

    private fun setupViewModels() {
        viewModel.showLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Success -> {
                    loadShow(state.data)
                }
                is ViewState.Loading -> {
                    with(binding) {
                        tvName.text = getString(R.string.loading)
                        tvGenres.text = ""
                        tvSummary.text = getString(R.string.loading)
                        tvTime.text = ""
                        tvDays.text = ""
                        rvEpisodeList.visibility = View.GONE
                        loadingView.root.visibility = View.VISIBLE
                    }
                }
                is ViewState.Failed -> {
                    AlertDialog.Builder(requireContext())
                        .setMessage("An error ocurred while loading the info about the show.")
                        .setOnDismissListener {
                            findNavController().popBackStack()
                        }
                }
            }
        }
        viewModel.episodeListLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.Success -> {
                    episodeAdapter.addItems(state.data)
                    val seasons = state.data.groupBy { it.season }.size
                    val episodes = state.data.size
                    binding.tvSeasons.text = "$seasons seasons"
                    binding.tvEpisodes.text = "$episodes episodes"
                }
                is ViewState.Loading -> {

                }
                is ViewState.Failed -> {

                }
            }
        }

        val showId = requireArguments().getInt("showId")
        viewModel.getShow(showId)
        viewModel.getEpisodes(showId)
    }

    private fun loadShow(show: Show) {
        with(binding) {
            Picasso.get()
                .load(show.imageUrl.medium)
                .into(ivPoster, onLoad {
                    icOpen.visibility = View.VISIBLE
                })
            ivPoster.setOnClickListener {
                findNavController().navigate(
                    R.id.action_showOverviewFragment_to_imageViewer,
                    bundleOf("url" to show.imageUrl.original)
                )
            }
            tvName.text = show.name
            tvGenres.text = show.genres.joinToString { it.uppercase() }
            tvSummary.text = show.summary.fromHTML()
            tvTime.text = show.schedule.time
            tvDays.text = show.schedule.days.joinToString()
            rvEpisodeList.visibility = View.VISIBLE
            loadingView.root.visibility = View.GONE
        }
    }

    override fun onEpisodeSelected(episode: Episode) {

    }

}