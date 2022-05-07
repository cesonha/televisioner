package dev.cesonha.televisioner.presentation.series.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.cesonha.televisioner.R
import dev.cesonha.televisioner.domain.entities.Series

class SeriesAdapter(
    private var currentSeries: MutableList<Series> = mutableListOf(),
    private var loadedSeries: MutableList<Series> = mutableListOf(),
    private val onSeriesTapListener: (series: Series) -> Unit
) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun addSeries(newSeries: List<Series>) {
        val oldNumberOfElements = loadedSeries.size
        loadedSeries.addAll(newSeries)
        currentSeries = loadedSeries
        notifyDataSetChanged()
//        notifyItemRangeInserted(
//            oldNumberOfElements,
//            oldNumberOfElements + newSeries.size
//        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        loadedSeries = mutableListOf()
        currentSeries = mutableListOf()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSeries(series: List<Series>) {
        currentSeries = series.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterSeries(filter: String) {
        currentSeries = if (filter.isEmpty()) {
            loadedSeries
        } else {
            loadedSeries.filter { it.name.lowercase().contains(filter.lowercase()) }.toMutableList()
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_series, viewGroup, false)

        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onSeriesTapListener.invoke(currentSeries[holder.adapterPosition])
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = currentSeries[position].name
        Picasso.get().load(currentSeries[position].imageData.originalUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return currentSeries.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.series_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.series_poster_image_view)
    }
}
