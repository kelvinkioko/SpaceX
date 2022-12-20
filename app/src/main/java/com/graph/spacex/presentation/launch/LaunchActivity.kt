package com.graph.spacex.presentation.launch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.graph.spacex.databinding.ActivityLaunchBinding
import com.graph.spacex.domain.model.LaunchAnalyticsModel
import com.graph.spacex.presentation.launch_sites.LaunchSiteFragment
import com.graph.spacex.util.observeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding
    private val viewModel: LaunchListViewModel by viewModels()
    private var bottomSheet: BottomSheetDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepObservers()
        setUpList()

        lifecycleScope.launchWhenResumed {
            viewModel.getListOfLaunches()
        }
    }

    private fun prepObservers() {
        viewModel.uiState.observeState(this, Lifecycle.State.STARTED) { state ->
            when (state) {
                LaunchListUIState.Default -> Unit
                is LaunchListUIState.Launches -> displayListsOfLaunches(launches = state.launches)
                is LaunchListUIState.Loading -> println("@@@ response Error ${state.isLoading}")
            }
        }
    }

    private val launchAdapter: LaunchAdapter by lazy {
        LaunchAdapter(
            launchSiteClicked = {
                val launchSitesFragment = LaunchSiteFragment(
                    siteImage = it.siteImage,
                    launches = it.launches
                )

                bottomSheet = launchSitesFragment
                bottomSheet?.show(supportFragmentManager, "Launch sites fragment")
            }
        )
    }

    private fun setUpList() {
        binding.launchList.adapter = launchAdapter
    }

    private fun displayListsOfLaunches(launches: List<LaunchAnalyticsModel>) {
        launchAdapter.submitList(launches)
    }
}