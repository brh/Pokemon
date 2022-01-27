package com.brh.poc.pokemon.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

typealias InflateMethod = (inflater: LayoutInflater, parent: ViewGroup, attach: Boolean)->ViewBinding

/**
 * A RecyclerView Adapter that makes use of ViewBinding and DataBinding views that are a part of RecyclerViews.
 * This removes the need of having to define a bunch of ViewHolders or repeated Adapter classes, by delegating
 * the work to the ViewBindingViewItem where you can define your view types, associate the view
 * [ViewBindingViewItem.createView] and bind the data [ViewBindingViewItem.data] to the view in [ViewBindingViewItem.bind] */
open class ViewBindingAdapter<Item: ViewBindingViewItem>(
    itemlist: List<Item>,
    val lifecycleOwner: LifecycleOwner? = null
):RecyclerView.Adapter<VBHolder2<Item>>()
{
    private var list: List<Item> = itemlist
        private set

    /**
     * A map of view (xml id) vs whether or not the ViewBindingViewItem is a Databinding vs a ViewBinding. True means it is ViewBinding
     */
    private val typeMap = mutableMapOf<Int, Any>()

    override fun getItemViewType(position: Int) = list[position].let {
        typeMap[it.viewType] = it.inflateMethod
            ?: it.bindingVar ?: throw RuntimeException("InflateMethod or BindingVar must be set")
        it.viewType
    }

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
        holder.bind(list[position], position)
    }

    override fun onViewRecycled(holder: VBHolder2<Item>) {
        super.onViewRecycled(holder)
        holder.recycled()
    }
    override fun getItemCount(): Int = list.size

    fun submitNewList(newList: List<Item>, diffCallback: ViewBindingDiffUtil<Item> = ViewBindingDiffUtil(newList, list)) {
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        list = newList
    }
}

/**
 * Outside of setting the adapter for the recycler this is the only class that you will need to extend
 * or really fully interact with
 * [viewType] The view type for this UI Model, in most case this can be the layout id (R.layout.somelayout), other than that it can be ignored
 * for cases of homogeneous recyclerviews
 * [bindingVar] [Int] The binding var at least one being used in the xml if this is Databinding
 * [inflateMethod] The inflate method for the ViewBinding or Databinding class. Example SomeViewBinding::inflate
 */
abstract class ViewBindingViewItem(
    val viewType: Int = 0,
    val bindingVar: Int? = null,
    val inflateMethod: ((inflater: LayoutInflater, parent: ViewGroup, attach: Boolean)->ViewBinding)? = null
)
{
    /** This is set right before the [bind] call **/
    lateinit var context: Context

    fun internalBind(binding: ViewBinding, position: Int) {
        context = binding.root.context
        if (binding is ViewDataBinding) {
            bindingVar?.let {  binding.setVariable(it, this) }
        }
        bind(binding, position)
    }

    /**
     * This method is responsible for binding the data onClick listeners, setting TextViews etc
     * [binding] the ViewBinding or DataBinding that was inflated/binded
     * [position] the position this item was pulled from the list, in case we alternate background colors
     * or something like that.
     */
    open fun bind(binding: ViewBinding, position: Int){}

    open fun recycled(){}
}

/**
 * The holder used internally
 */
class VBHolder2<T: ViewBindingViewItem>(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private var _item: T? = null

    fun bind(item: T, position: Int) {
        _item = item
        item.internalBind(binding, position)
    }

    fun recycled() {
        _item?.recycled()
    }
}

open class ViewBindingDiffUtil<T>(val newList: List<T>, val oldList: List<T>): DiffUtil.Callback() {
    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun getOldListSize() = oldList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = false

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean = false

}

/**
 * Convenience method to get the LayoutInflater
 */
fun View.inflater() = LayoutInflater.from(context)