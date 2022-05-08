package dev.cesonha.televisioner.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.core.Constants.Companion.SERIES_ID_ARG
import dev.cesonha.televisioner.databinding.FragmentFavoritesBinding
import dev.cesonha.televisioner.presentation.favorites.FavoritesViewModel.FavoritesFragmentState
import dev.cesonha.televisioner.presentation.series.list.SeriesAdapter

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SeriesAdapter

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val navController = findNavController()
        adapter = SeriesAdapter(onSeriesTapListener = {
            val bundle = bundleOf(SERIES_ID_ARG to it.id)
            navController.navigate(
                R.id.action_navigation_favorites_to_navigation_series_details,
                bundle
            )
        }, loadMoreListener = {
        })

        val recyclerView = binding.favoritesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavoritesFragmentState.Loading -> {
                    binding.favoritesProgressBar.visibility = View.VISIBLE
                    binding.emptyFavoritesTextView.visibility = View.INVISIBLE
                    binding.favoritesRecyclerView.visibility = View.INVISIBLE
                }
                is FavoritesFragmentState.Success -> {
                    state.data?.let {
                        adapter.setSeries(it)
                    }
                    binding.favoritesRecyclerView.visibility = View.VISIBLE
                    binding.emptyFavoritesTextView.visibility = View.INVISIBLE
                    binding.favoritesProgressBar.visibility = View.INVISIBLE
                }
                is FavoritesFragmentState.Empty -> {
                    binding.emptyFavoritesTextView.visibility = View.VISIBLE
                    binding.favoritesRecyclerView.visibility = View.INVISIBLE
                    binding.favoritesProgressBar.visibility = View.INVISIBLE
                }
                is FavoritesFragmentState.Error -> {
                }
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        adapter.clearData()
        viewModel.fetchFavoriteSeries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
