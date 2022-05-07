package dev.cesonha.televisioner.presentation.series.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.domain.entities.Episode
import java.lang.Exception

class EpisodesAdapter(
    private var episodes: MutableList<Episode>,
    private var shownEpisodes: MutableList<Episode> = mutableListOf(),

    private val onEpisodeTapListener: (episode: Episode) -> Unit
) :
    RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    fun addItems(episodes: List<Episode>, season: Int) {
        this.episodes.addAll(episodes)
        showSeasonEpisodes(season)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showSeasonEpisodes(season: Int) {
        shownEpisodes = episodes.filter { it.season == season }.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_episode_details, viewGroup, false)

        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onEpisodeTapListener.invoke(shownEpisodes[holder.adapterPosition])
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = holder.itemView.context.getString(
            R.string.episode_number,
            position + 1, shownEpisodes[position].name
        )

        val pos = position
        Picasso.get().load(shownEpisodes[pos].image.mediumQualityUrl).into(
            holder.imageView,
            object : Callback {
                override fun onSuccess() {
                    if (pos % 2 == 0) {
                        holder.constraintLayout.background = AppCompatResources.getDrawable(holder.itemView.context, R.color.purple_50)
                    } else {
                        holder.constraintLayout.background = AppCompatResources.getDrawable(holder.itemView.context, R.color.white)
                    }
                }
                override fun onError(e: Exception?) {
                }
            }
        )
    }

    override fun getItemCount(): Int {
        return shownEpisodes.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.episode_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.episode_image_view)
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.item_episode_details_layout)
    }
}
