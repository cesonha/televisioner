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
import dev.cesonha.televisioner.domain.entities.ImageData
import dev.cesonha.televisioner.domain.entities.Series

class SearchAdapter(
    private var series: MutableList<Series> = mutableListOf(),
    private val onSeriesTapListener: (series: Series) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setSeries(series: List<Series>) {
        this.series = series.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_search, viewGroup, false)

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
        if (series[position].imageData == null) {
            series[position].imageData = ImageData(NO_IMAGE_URL, NO_IMAGE_URL)
        }
        Picasso.get().load(series[position].imageData?.mediumQualityUrl ?: NO_IMAGE_URL).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return series.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.search_item_name)
        val imageView: ImageView = view.findViewById(R.id.search_item_image)
    }
}
