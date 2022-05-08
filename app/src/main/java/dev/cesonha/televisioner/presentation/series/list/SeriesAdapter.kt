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
import dev.cesonha.televisioner.core.Constants.Companion.NO_IMAGE_URL
import dev.cesonha.televisioner.domain.entities.Series

class SeriesAdapter(
    private var series: MutableList<Series> = mutableListOf(),
    private val onSeriesTapListener: (series: Series) -> Unit,
    private val loadMoreListener: () -> Unit
) :
    RecyclerView.Adapter<SeriesAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        series = mutableListOf()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSeries(series: List<Series>) {
        this.series = series.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_series, viewGroup, false)

        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onSeriesTapListener.invoke(series[holder.adapterPosition])
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = series[position].name
        Picasso.get().load(series[position].imageData?.originalUrl ?: NO_IMAGE_URL).into(holder.imageView)

        if (position == series.size - 1) {
            loadMoreListener()
        }
    }

    override fun getItemCount(): Int {
        return series.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.series_name_text_view)
        val imageView: ImageView = view.findViewById(R.id.series_poster_image_view)
    }
}
