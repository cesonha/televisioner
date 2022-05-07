package dev.cesonha.televisioner.presentation.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.core.Constants.Companion.EPISODE_ID_ARG
import dev.cesonha.televisioner.databinding.FragmentEpisodeBinding

@AndroidEntryPoint
class EpisodeFragment : Fragment() {
    private var _binding: FragmentEpisodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: EpisodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.fetchEpisodeDetails(arguments?.get(EPISODE_ID_ARG) as Int)

        viewModel.episode.observe(viewLifecycleOwner) {
            binding.nameTextView.text = it.name
            binding.episodeTextView.text =
                getString(R.string.episode_season_and_number, it.season, it.number)
            binding.summaryTextView.text = it.summary

            Picasso.get().load(it.image.mediumQualityUrl).into(binding.episodeImageView)
        }

        viewModel.state.observe(viewLifecycleOwner) {
            if (it == EpisodeViewModel.EpisodeFragmentState.SUCCESS) {
                binding.episodeImageView.visibility = View.VISIBLE
                binding.infoConstraintLayout.visibility = View.VISIBLE
                binding.summaryTextView.visibility = View.VISIBLE
                binding.episodeProgressBar.visibility = View.INVISIBLE
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
