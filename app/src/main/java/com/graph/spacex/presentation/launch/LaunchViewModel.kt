package com.graph.spacex.presentation.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graph.spacex.domain.model.LaunchAnalyticsModel
import com.graph.spacex.domain.repository.SpaceRepository
import com.graph.spacex.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val spaceRepository: SpaceRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<LaunchListUIState>(LaunchListUIState.Default)
    val uiState = _uiState.asStateFlow()

    fun getListOfLaunches() {
        viewModelScope.launch {
            try {
                val response = spaceRepository.getListOfLaunches()

                response.onEach { resource ->
                    when(resource) {
                        is Resource.Error -> println("@@@ response Error ${resource.message}")
                        is Resource.Loading -> println("@@@ response Loading ${resource.isLoading}")
                        is Resource.Success -> _uiState.value = LaunchListUIState.Launches(
                            launches = resource.data ?: emptyList()
                        )
                    }
                }.launchIn(this)
            } catch (exception: Throwable) {
                println("@@@ ${exception.message}")
            }
        }
    }
}

sealed class LaunchListUIState {
    object Default : LaunchListUIState()
    data class Loading(val isLoading: Boolean = true) : LaunchListUIState()
    data class Launches(val launches: List<LaunchAnalyticsModel> = emptyList()) : LaunchListUIState()
}