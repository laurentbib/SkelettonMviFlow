package com.biblublab.skelettonmviflow.homeFeature

import android.view.View
import com.biblublab.domain.homeFeature.entities.HomeNews
import com.biblublab.skelettonmviflow.R
import com.biblublab.skelettonmviflow.common.adapter.ViewBindingAdapterItem
import com.biblublab.skelettonmviflow.common.custom.loadImg
import com.biblublab.skelettonmviflow.databinding.ItemViewBinding

class HomeItemView (private val news : HomeNews) : ViewBindingAdapterItem{

    override val layoutId: Int = R.layout.item_view

    fun bind(binding : ItemViewBinding, listener : (View, news : HomeNews) -> Unit) {
        binding.newsTitle.text = news.title
        binding.newsDescription.text = news.description
        binding.newsImage.loadImg(news.urlImage, R.drawable.ic_launcher_background)
        binding.root.setOnClickListener{listener(it, news)}
    }
}