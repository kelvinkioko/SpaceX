package com.graph.spacex.domain.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.graph.spacex.LaunchListQuery
import com.graph.spacex.domain.model.LaunchAnalyticsModel
import com.graph.spacex.domain.model.LaunchListModel
import com.graph.spacex.util.Resource
import kotlinx.coroutines.flow.Flow

interface SpaceRepository {
    suspend fun getListOfLaunches(): Flow<Resource<List<LaunchAnalyticsModel>>>
}