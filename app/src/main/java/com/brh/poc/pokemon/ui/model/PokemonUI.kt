package com.brh.poc.pokemon.ui.model

import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import coil.load
import com.brh.poc.pokemon.BR
import com.brh.poc.pokemon.R
import com.brh.poc.pokemon.databinding.ViewListItemBinding
import com.brh.poc.pokemon.ui.adapter.ViewBindingViewItem
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PokemonUI(val name: String, val id: Int, val details: Deferred<PokemonDetailUI>) :
    ViewBindingViewItem(R.layout.view_list_item, BR.pokemon) {

    override fun bind(binding: ViewBinding, position: Int) {
        super.bind(binding, position)
        binding as ViewListItemBinding
        binding.ivIcon.setImageDrawable(null)
        binding.progressbar.isVisible = true
        GlobalScope.launch(Dispatchers.Main) {
            details.await()?.let {
                binding.progressbar.isVisible = false
                binding.ivIcon.load(it.image)
            }
        }
    }
}