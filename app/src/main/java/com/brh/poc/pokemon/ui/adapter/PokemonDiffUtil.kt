package com.brh.poc.pokemon.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.brh.poc.pokemon.ui.model.PokemonUI

class PokemonDiffUtil: DiffUtil.ItemCallback<PokemonUI>() {

    override fun areItemsTheSame(oldItem: PokemonUI, newItem: PokemonUI) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PokemonUI, newItem: PokemonUI) = areItemsTheSame(oldItem, newItem)
}