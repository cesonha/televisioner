package dev.cesonha.televisioner.domain.entities

import com.google.gson.annotations.SerializedName

data class SearchResult(@SerializedName("show") val series: Series)
