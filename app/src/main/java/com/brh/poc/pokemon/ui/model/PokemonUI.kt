package com.brh.poc.pokemon.ui.model

import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import coil.load
import com.brh.poc.pokemon.BR
import com.brh.poc.pokemon.R
import com.brh.poc.pokemon.databinding.ViewListItemBinding
import com.brh.poc.pokemon.ui.adapter.ViewBindingViewItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PokemonUI(val name: String, val id: Int, val details: Deferred<PokemonDetailUI>) :
    ViewBindingViewItem(R.layout.view_list_item, BR.pokemon) {
    private var imageJob: Job? = null
    private var _binding: ViewListItemBinding? = null

    override fun bind(binding: ViewBinding, position: Int) {
        super.bind(binding, position)
        _binding = binding as ViewListItemBinding
        binding.ivIcon.setImageDrawable(null)
        binding.ivIcon.isVisible = false
        binding.progressbar.isVisible = true
        imageJob = puScope.launch(Dispatchers.Main) {
            details.await().let {
                if (this.isActive) {
                    binding.progressbar.isVisible = false
                    binding.ivIcon.load(it.image)
                    binding.ivIcon.isVisible = true
                } else
                    binding.ivIcon.isVisible = false
            }
        }
    }

    override fun recycled() {
        _binding?.apply {
            ivIcon.isVisible = false
            progressbar.isVisible = true
        }
        imageJob?.cancel()
        super.recycled()
    }

    companion object {
        val puScope = CoroutineScope(Dispatchers.IO)
    }
}