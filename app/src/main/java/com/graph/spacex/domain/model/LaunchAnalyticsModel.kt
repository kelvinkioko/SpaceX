package com.graph.spacex.domain.model

import androidx.annotation.DrawableRes

data class LaunchAnalyticsModel(
    val site: String = "",
    @DrawableRes val siteImage: Int = 0,
    val availableLaunches: String = "",
    val launches: List<LaunchListModel> = emptyList()
)
