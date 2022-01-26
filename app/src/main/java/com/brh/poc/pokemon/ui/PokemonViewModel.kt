package com.brh.poc.pokemon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.brh.poc.pokemon.repository.network.PokemonRepository
import com.brh.poc.pokemon.ui.model.PokemonUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PokemonViewModel(val repository: PokemonRepository, val pager: Pager<Int, PokemonUI>): ViewModel() {

    val pagerFlow = pager.flow.cachedIn(viewModelScope)
    fun getPokemonList() {
        viewModelScope.launch {
            val list = repository.getPokemonList(0)
        }
    }
}