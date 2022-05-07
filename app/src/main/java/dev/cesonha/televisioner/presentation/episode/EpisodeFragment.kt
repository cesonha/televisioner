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
import dev.cesonha.televisioner.domain.entities.Episode

@AndroidEntryPoint
class EpisodeFragment : Fragment() {
    private var _binding: FragmentEpisodeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: EpisodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchEpisodeDetails(arguments?.get(EPISODE_ID_ARG) as Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.state.observe(viewLifecycleOwner) {
            if (it is EpisodeViewModel.EpisodeFragmentState.Success) {
                binding.episodeImageView.visibility = View.VISIBLE
                binding.infoConstraintLayout.visibility = View.VISIBLE
                binding.summaryTextView.visibility = View.VISIBLE
                binding.episodeProgressBar.visibility = View.INVISIBLE

                it.data?.let { episode ->
                    loadEpisodeInfo(episode)
                }
            }
        }
        return root
    }

    private fun loadEpisodeInfo(episode: Episode) {
        binding.nameTextView.text = episode.name
        binding.episodeTextView.text =
            getString(R.string.episode_season_and_number, episode.season, episode.number)
        binding.summaryTextView.text = episode.summary

        Picasso.get().load(episode.image.mediumQualityUrl).into(binding.episodeImageView)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
