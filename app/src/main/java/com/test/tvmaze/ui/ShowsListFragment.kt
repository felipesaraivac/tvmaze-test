package com.test.tvmaze.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.test.tvmaze.R
import com.test.tvmaze.data.model.Show
import com.test.tvmaze.databinding.ShowsListFragmentBinding
import com.test.tvmaze.ui.adapters.ShowAdapter
import com.test.tvmaze.ui.viewmodel.ShowsListViewModel
import com.test.tvmaze.ui.viewmodel.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowsListFragment : Fragment(), ShowAdapter.OnSelectShow {

    private val viewModel by viewModel<ShowsListViewModel>()
    private var page: Int = 0
    private var showAdapter = ShowAdapter(this)
    private lateinit var rvShow: RecyclerView
    private var _binding: ShowsListFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ShowsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        rvShow = binding.rvShowList
        rvShow.adapter = showAdapter
    }

    private fun setupViewModels() {
        viewModel.showListLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ViewState.Success -> {
                    if (state.data.isNotEmpty()){
                        showAdapter.addItems(state.data)
                    }
                }
                is ViewState.Loading -> {

                }
            }
        }

        viewModel.loadShows(0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchView = menu.findItem(R.id.search_item).actionView as SearchView
        searchView.apply {
            setIconifiedByDefault(true)
            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
            layoutParams = params
            setOnQueryTextListener(onQueryTextChanged)
        }
    }

    private val onQueryTextChanged = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true
        override fun onQueryTextChange(newText: String?): Boolean {
            if (newText != null) {
                viewModel.search(newText)
            }
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onShowSelected(show: Show) {
        findNavController().navigate(R.id.action_showsListFragment_to_showOverviewFragment, bundleOf("showId" to show.id))
    }

}