package com.brh.poc.pokemon.repository.network

import android.net.Uri
import com.brh.poc.pokemon.ui.model.PokemonDetailUI
import com.brh.poc.pokemon.ui.model.PokemonPageResponse
import com.brh.poc.pokemon.ui.model.PokemonType
import com.brh.poc.pokemon.ui.model.PokemonUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class PokemonRepositoryImpl(val api: PokemonApi) : PokemonRepository {

    private val repoScope = CoroutineScope(Dispatchers.IO)

    override suspend fun getPokemonList(offset: Int): PokemonPageResponse {
        val listResult = api.getPokemonList(offset)
        val uiList = listResult.results.map { item ->
            val id = item.url.extractIdFromUrl()
            val deferred: Deferred<PokemonDetailUI> = repoScope.async(start = CoroutineStart.LAZY) {
                val result = api.getPokemonDetails(id)
                PokemonDetailUI(result.sprites.frontDefault, emptyList(), PokemonType("f"))
            }
            PokemonUI(item.name, id, deferred)
        }
        return PokemonPageResponse(
            listResult.next?.getOffset(),
            listResult.previous?.getOffset(),
            uiList
        )
    }

    private fun String.extractIdFromUrl() =
        Uri.parse(this).lastPathSegment?.toInt() ?: throw RuntimeException()

    private fun String.getOffset() =
        Uri.parse(this).getQueryParameter("offset")?.toInt() ?: throw RuntimeException()

}