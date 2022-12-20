package com.graph.spacex.presentation.launch_sites

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.graph.spacex.databinding.FragmentLaunchSitesBinding
import com.graph.spacex.domain.model.LaunchListModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchSiteFragment(
    @DrawableRes private val siteImage: Int,
    private val launches: List<LaunchListModel> = emptyList()
): BottomSheetDialogFragment() {

    private var _binding: FragmentLaunchSitesBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLaunchSitesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

            val params = bottomSheet.layoutParams
            val height: Int = Resources.getSystem().displayMetrics.heightPixels
            params.height = (height * 0.90).toInt()
            bottomSheet.layoutParams = params

            BottomSheetBehavior.from(bottomSheet).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                skipCollapsed = true
                isDraggable = true
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderToolBarImage()
        setUpList()
        displayListsOfLaunches()
    }

    private fun renderToolBarImage() {
        binding.toolbarImage.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                siteImage,
                resources.newTheme()
            )
        )
    }

    private val launchSiteAdapter: LaunchSiteAdapter by lazy { LaunchSiteAdapter() }

    private fun setUpList() {
        binding.launchSites.adapter = launchSiteAdapter
    }

    private fun displayListsOfLaunches() {
        launchSiteAdapter.submitList(launches)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}