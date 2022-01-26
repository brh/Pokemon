package com.brh.poc.pokemon.repository.network

import com.brh.poc.pokemon.ui.model.PokemonPageResponse

interface PokemonRepository {

    suspend fun getPokemonList(offset: Int): PokemonPageResponse

}