package dev.cesonha.televisioner.presentation.series.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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

    private val currentPage = 0
    private lateinit var adapter: SeriesAdapter
    private val viewModel: SeriesListViewModel by viewModels()

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
        adapter = SeriesAdapter(mutableListOf(), mutableListOf()) {
            val bundle = bundleOf(SERIES_ID_ARG to it.id)
            navController.navigate(
                R.id.action_navigation_series_to_navigation_series_details,
                bundle
            )
        }

        val recyclerView = binding.seriesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.seriesSearchView.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    adapter.filterSeries(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        adapter.filterSeries("")
                    }
                    return true
                }
            })

        viewModel.series.observe(viewLifecycleOwner) {
            adapter.addSeries(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
