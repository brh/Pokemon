package com.brh.poc.pokemon.repository.network

import com.brh.poc.pokemon.di.PAGING_SIZE
import com.brh.poc.pokemon.repository.network.model.PokemonDetail
import com.brh.poc.pokemon.repository.network.model.PokemonListResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemonList(@Query("offset") offset: Int = 0, @Query("limit") limit: Int = PAGING_SIZE): PokemonListResult

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetail
}