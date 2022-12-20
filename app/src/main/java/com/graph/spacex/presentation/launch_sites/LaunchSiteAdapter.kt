package com.graph.spacex.presentation.launch_sites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.graph.spacex.databinding.ItemLaunchSiteBinding
import com.graph.spacex.domain.model.LaunchListModel

class LaunchSiteAdapter: ListAdapter<LaunchListModel, LaunchSiteAdapter.LaunchSiteViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchSiteViewHolder {
        return LaunchSiteViewHolder(
            ItemLaunchSiteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LaunchSiteViewHolder, position: Int) {
        holder.bind(launch = currentList[position])
    }

    inner class LaunchSiteViewHolder(
        private val binding: ItemLaunchSiteBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: LaunchListModel) {
            binding.apply {
                siteName.text = launch.missionName
                siteLaunches.text = launch.rocketName
            }
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<LaunchListModel>() {
            override fun areItemsTheSame(
                oldItem: LaunchListModel,
                newItem: LaunchListModel
            ): Boolean { return oldItem == newItem }

            override fun areContentsTheSame(
                oldItem: LaunchListModel,
                newItem: LaunchListModel
            ): Boolean { return oldItem == newItem }
        }
    }
}