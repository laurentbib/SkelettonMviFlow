package com.biblublab.skelettonmviflow.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class ViewBindingAdapter<Item : ViewBindingAdapterItem, VB : ViewBinding>(
    diffCallback: ViewBindingDiffUtilCallback<Item>
) : ListAdapter<Item, ViewBindingViewHolder<Item, VB>>(diffCallback) {

    override fun onBindViewHolder(holder: ViewBindingViewHolder<Item, VB>, position: Int) {
        holder.bind(item = getItem(position))
    }

    override fun getItemViewType(position: Int): Int  = getItem(position).layoutId

    protected val ViewGroup.layoutInflater: LayoutInflater
        get() = LayoutInflater.from(this.context)
}