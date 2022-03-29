package com.brh.poc.pokemon.ui.adapter

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

/**
 * A RecyclerView Adapter that makes use of ViewBinding and DataBinding views that are a part of RecyclerViews.
 * This removes the need of having to define a bunch of ViewHolders or repeated Adapter classes, by delegating
 * the work to the ViewBindingViewItem where you can define your view types, associate the view
 * [ViewBindingViewItem.createView] and bind the data [ViewBindingViewItem.data] to the view in [ViewBindingViewItem.bind] */
open class ViewBindingPagingAdapter<Item: ViewBindingViewItem>(
    private val lifecycleOwner: LifecycleOwner? = null,
    diffUtil: DiffUtil.ItemCallback<Item>,
    val onClickCallback: ViewBindingItemCallback? = null
): PagingDataAdapter<Item, VBHolder2<Item>>(diffUtil)
{
    /**
     * A map of view (xml id) vs whether or not the ViewBindingViewItem is a Databinding vs a ViewBinding. True means it is ViewBinding
     */
    private val typeMap = mutableMapOf<Int, Any>()

    override fun getItemViewType(position: Int) = getItem(position)?.let {
        typeMap[it.viewType] = it.inflateMethod
            ?: it.bindingVar ?: throw RuntimeException("InflateMethod or BindingVar must be set")
        it.viewType
    } ?: 0 //default to zero if the value returned is a placeholder as stated in PagingDataAdapter docs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VBHolder2<Item> {
        return typeMap[viewType]?.let {
            val binding: ViewBinding = when (it){
                is Int -> {
                    DataBindingUtil.inflate(parent.inflater(), viewType, parent, false)
                }
                else -> {
                    (it as InflateMethod).invoke(parent.inflater(), parent, false)
                }
            }
            lifecycleOwner?.let {
                if (binding is ViewDataBinding)
                    binding.lifecycleOwner = it
            }
            VBHolder2(binding)
        } ?: throw RuntimeException("Please set a type")
    }

    override fun onBindViewHolder(holder: VBHolder2<Item>, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
        item?.onClick = onClickCallback
    }
}
