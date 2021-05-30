package com.biblublab.skelettonmviflow.common.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingViewHolder<Item : ViewBindingAdapterItem, out VB : ViewBinding>(protected val binding : VB) : RecyclerView.ViewHolder(binding.root){

    abstract fun bind(item : Item)
}