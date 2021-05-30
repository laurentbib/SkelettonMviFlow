package com.biblublab.skelettonmviflow.homeFeature

import android.view.View
import android.view.ViewGroup
import com.biblublab.domain.homeFeature.entities.HomeNews
import com.biblublab.skelettonmviflow.common.adapter.ViewBindingAdapter
import com.biblublab.skelettonmviflow.common.adapter.ViewBindingViewHolder
import com.biblublab.skelettonmviflow.databinding.ItemViewBinding

class HomeAdapter(private val listener : (View, news : HomeNews) -> Unit) : ViewBindingAdapter<HomeItemView, ItemViewBinding>(HomeItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingViewHolder<HomeItemView, ItemViewBinding> {
val inflater = parent.layoutInflater
     val binding = ItemViewBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding)
    }

  inner class HomeViewHolder(viewBinding : ItemViewBinding) : ViewBindingViewHolder<HomeItemView, ItemViewBinding>(viewBinding) {
        override fun bind(item: HomeItemView) {
            item.bind(binding, listener)
        }
    }


}