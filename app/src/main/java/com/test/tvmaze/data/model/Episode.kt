package com.test.tvmaze.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Episode(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("season")
    var season: Int = 0,
    @SerializedName("number")
    var number: Int = 0,
    @SerializedName("image")
    var imageUrl: Image = Image(),
    @SerializedName("airdate")
    var airdate: Date = Date(),
    @SerializedName("summary")
    var summary: String = ""
)
