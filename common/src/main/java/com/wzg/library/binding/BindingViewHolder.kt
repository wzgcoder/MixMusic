package com.wzg.library.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/4/29 5:43 下午
 */

inline fun <reified VB : ViewBinding> BindingViewHolder(parent: ViewGroup)=
    BindingViewHolder(inflateBinding<VB>(parent))

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup, inflater: (LayoutInflater, ViewGroup, Boolean) -> VB) :
            this(inflater(LayoutInflater.from(parent.context), parent, false))
}