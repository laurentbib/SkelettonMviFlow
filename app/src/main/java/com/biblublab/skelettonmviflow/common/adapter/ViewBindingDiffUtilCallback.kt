package com.biblublab.skelettonmviflow.common.adapter

import androidx.recyclerview.widget.DiffUtil

abstract class ViewBindingDiffUtilCallback<Item : ViewBindingAdapterItem> :  DiffUtil.ItemCallback<Item>()