package com.graph.spacex.data.repository

import com.graph.spacex.LaunchListQuery
import com.graph.spacex.R
import com.graph.spacex.app.SpaceClient
import com.graph.spacex.domain.model.LaunchAnalyticsModel
import com.graph.spacex.domain.model.LaunchListModel
import com.graph.spacex.domain.repository.SpaceRepository
import com.graph.spacex.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpaceRepositoryImpl @Inject constructor() : SpaceRepository {
    override suspend fun getListOfLaunches(): Flow<Resource<List<LaunchAnalyticsModel>>> {
        val listLaunches = mutableListOf<LaunchAnalyticsModel>()
        val siteDrawableRes = intArrayOf(
            R.drawable.space_launch_one,
            R.drawable.space_launch_two,
            R.drawable.space_launch_three,
            R.drawable.space_launch_four,
            R.drawable.space_launch_five
        )
        val launches = SpaceClient.apolloClient.query(LaunchListQuery()).execute()
        launches.data?.launches?.launches?.let { launchData ->
            val groupedLaunches = launchData.groupBy { it?.site ?: "" }
            var position = 0
            groupedLaunches.map {
                val launchesList = mutableListOf<LaunchListModel>()
                it.value.map { launch ->
                    launch?.let {
                        launchesList.add(
                            LaunchListModel(
                                launchID = launch.id,
                                launchSite = launch.site ?: "",
                                missionName = launch.mission?.name ?: "",
                                rocketName = launch.rocket?.name ?: ""
                            )
                        )
                    }
                }

                listLaunches.add(
                    LaunchAnalyticsModel(
                        site = it.key,
                        siteImage = siteDrawableRes[position],
                        availableLaunches = "${it.value.size} launch${if(it.value.size > 1) "es" else ""}",
                        launches = launchesList
                    )
                )

                position = if (position == 4) 0 else position + 1
            }
        }
        return flow { emit(Resource.Success(data = listLaunches)) }
    }
}