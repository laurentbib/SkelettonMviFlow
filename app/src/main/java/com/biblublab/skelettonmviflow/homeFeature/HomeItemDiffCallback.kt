package com.biblublab.skelettonmviflow.homeFeature

import com.biblublab.skelettonmviflow.common.adapter.ViewBindingDiffUtilCallback

object HomeItemDiffCallback : ViewBindingDiffUtilCallback<HomeItemView>() {
    override fun areItemsTheSame(oldItem: HomeItemView, newItem: HomeItemView): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: HomeItemView, newItem: HomeItemView): Boolean {
        return oldItem == newItem
    }
}