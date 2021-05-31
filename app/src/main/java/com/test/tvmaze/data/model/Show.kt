package com.test.tvmaze.data.model

import com.google.gson.annotations.SerializedName

data class Show(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("language")
    var language: String = "",
    @SerializedName("image")
    var imageUrl: Image = Image(),
    @SerializedName("schedule")
    var schedule: Schedule = Schedule(),
    @SerializedName("genres")
    var genres: List<String> = emptyList(),
    @SerializedName("summary")
    var summary: String = ""
)

data class Schedule(
    @SerializedName("time")
    var time: String = "",
    @SerializedName("days")
    var days: List<String> = emptyList()
)