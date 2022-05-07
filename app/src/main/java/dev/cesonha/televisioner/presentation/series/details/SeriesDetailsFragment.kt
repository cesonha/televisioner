package dev.cesonha.televisioner.presentation.series.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.core.Constants.Companion.EPISODE_ID_ARG
import dev.cesonha.televisioner.core.Constants.Companion.SERIES_ID_ARG
import dev.cesonha.televisioner.databinding.FragmentSeriesDetailsBinding

@AndroidEntryPoint
class SeriesDetailsFragment : Fragment() {
    private var _binding: FragmentSeriesDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: EpisodesAdapter

    private val viewModel: SeriesDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchSeriesDetails(arguments?.get(SERIES_ID_ARG) as Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSeriesDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        viewModel.fetchSeriesDetails(arguments?.get("seriesId") as Int)

        val navController = findNavController()
        adapter = EpisodesAdapter(mutableListOf()) {
            val bundle = bundleOf(EPISODE_ID_ARG to it.id)
            navController.navigate(R.id.action_navigation_series_details_to_navigation_episode, bundle)
        }

        val recyclerView = binding.seasonsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.seriesFavoriteButton.setOnClickListener {
            viewModel.series.value?.let {
                viewModel.onFavoriteButtonTap(it)
            }
        }

        viewModel.favorited.observe(viewLifecycleOwner) {
            if (it == true) {
                binding.seriesFavoriteButton.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_favorite_on,
                        context?.theme
                    )
                )
            } else {
                binding.seriesFavoriteButton.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources, R.drawable.ic_favorite_off,
                        context?.theme
                    )
                )
            }
        }

        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                SeriesDetailsViewModel.SeriesDetailsFragmentState.LOADING -> {
                    binding.seriesDetailsLayout.visibility = View.INVISIBLE
                    binding.seriesDetailsProgressBar.visibility = View.VISIBLE
                    binding.seasonSpinner.visibility = View.INVISIBLE
                    binding.seasonsRecyclerView.visibility = View.INVISIBLE
                    binding.seriesDetailsScroll.visibility = View.INVISIBLE
                }
                SeriesDetailsViewModel.SeriesDetailsFragmentState.SUCCESS -> {
                    binding.seriesDetailsLayout.visibility = View.VISIBLE
                    binding.seriesDetailsProgressBar.visibility = View.INVISIBLE
                    binding.seasonSpinner.visibility = View.VISIBLE
                    binding.seasonsRecyclerView.visibility = View.VISIBLE
                    binding.seriesDetailsScroll.visibility = View.VISIBLE
                }
                SeriesDetailsViewModel.SeriesDetailsFragmentState.ERROR -> {
                }
                else -> {
                }
            }
        }

        viewModel.series.observe(viewLifecycleOwner) {
            binding.seriesNameTextView.text = it.name
            binding.genresTextView.text = it.genres.joinToString("|")
            binding.seriesDaysTimeTextView.text = it.scheduleData.days.joinToString(",")
            binding.seriesSummaryTextView.text = it.summary
            Picasso.get().load(it.imageData.mediumQualityUrl).into(binding.seriesPosterImageView)

            val spinner = binding.seasonSpinner
            val seasons = it.episodesData.episodes.last().season
            val arraySpinner = (1..seasons).map { x -> getString(R.string.season, x) }.toTypedArray()
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item, arraySpinner
            )
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = spinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                    adapter.showSeasonEpisodes(position + 1)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            adapter.addItems(it.episodesData.episodes, 1)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
