package dev.cesonha.televisioner.presentation.series.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.core.Constants.Companion.SERIES_ID_ARG
import dev.cesonha.televisioner.databinding.FragmentSeriesBinding

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    private var _binding: FragmentSeriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var currentPage = 0
    private lateinit var adapter: SeriesAdapter
    private val viewModel: SeriesListViewModel by viewModels()

    private lateinit var searchAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchSeries(currentPage)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()
        adapter = SeriesAdapter(mutableListOf(), {
            val bundle = bundleOf(SERIES_ID_ARG to it.id)
            navController.navigate(
                R.id.action_navigation_series_to_navigation_series_details,
                bundle
            )
        }, {
            currentPage++
            viewModel.fetchSeries(currentPage)
        })

        searchAdapter = SearchAdapter(mutableListOf()) {
            val bundle = bundleOf(SERIES_ID_ARG to it.id)
            navController.navigate(
                R.id.action_navigation_series_to_navigation_series_details,
                bundle
            )
        }

        val recyclerView = binding.seriesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        val searchRecyclerView = binding.searchRecyclerView
        searchRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        searchRecyclerView.adapter = searchAdapter

        binding.seriesSearchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    searchViewModel.fetchSeries(newText)
                    return true
                }
            })

        searchViewModel.series.observe(viewLifecycleOwner) {
            searchRecyclerView.visibility = View.VISIBLE
            searchAdapter.setSeries(it)
        }

        viewModel.series.observe(viewLifecycleOwner) {
            adapter.setSeries(it)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                SeriesListViewModel.SeriesListFragmentState.LOADING -> {
                    binding.seriesSearchView.visibility = View.INVISIBLE
                    binding.seriesRecyclerView.visibility = View.INVISIBLE
                    binding.searchRecyclerView.visibility = View.INVISIBLE
                    binding.seriesListProgress.visibility = View.VISIBLE
                }
                SeriesListViewModel.SeriesListFragmentState.SUCCESS -> {
                    binding.seriesSearchView.visibility = View.VISIBLE
                    binding.seriesRecyclerView.visibility = View.VISIBLE
                    binding.seriesListProgress.visibility = View.INVISIBLE
                }
                SeriesListViewModel.SeriesListFragmentState.ERROR -> {
                    binding.seriesSearchView.visibility = View.INVISIBLE
                    binding.seriesRecyclerView.visibility = View.INVISIBLE
                    binding.searchRecyclerView.visibility = View.INVISIBLE
                    binding.seriesListProgress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "teste", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
