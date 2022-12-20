package com.graph.spacex.presentation.launch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graph.spacex.databinding.ItemLaunchBinding
import com.graph.spacex.domain.model.LaunchAnalyticsModel

class LaunchAdapter(
    private val launchSiteClicked: (LaunchAnalyticsModel) -> Unit
): ListAdapter<LaunchAnalyticsModel, LaunchAdapter.LaunchListViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        return LaunchListViewHolder(
            ItemLaunchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        holder.bind(launch = currentList[position])
    }

    inner class LaunchListViewHolder(
        private val binding: ItemLaunchBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: LaunchAnalyticsModel) {
            binding.apply {
                val context = missionImageView.context
                siteName.text = launch.site
                siteLaunches.text = launch.availableLaunches
                missionImageView.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        launch.siteImage,
                        context.theme
                    )
                )
            }

            itemView.setOnClickListener {
                launchSiteClicked.invoke(launch)
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<LaunchAnalyticsModel>() {
            override fun areItemsTheSame(
                oldItem: LaunchAnalyticsModel,
                newItem: LaunchAnalyticsModel
            ): Boolean { return oldItem == newItem }

            override fun areContentsTheSame(
                oldItem: LaunchAnalyticsModel,
                newItem: LaunchAnalyticsModel
            ): Boolean { return oldItem == newItem }
        }
    }
}