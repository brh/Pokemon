package com.brh.poc.pokemon.repository.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class PokemonListResult(
    val count: Int,
    val results: List<PokemonListItem>,
    val next: String?,
    val previous: String?
)

@JsonClass(generateAdapter = true)
class PokemonListItem(val name: String, val url: String)