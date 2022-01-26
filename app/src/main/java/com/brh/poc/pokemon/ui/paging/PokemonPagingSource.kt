package com.brh.poc.pokemon.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.brh.poc.pokemon.di.PAGING_SIZE
import com.brh.poc.pokemon.repository.network.PokemonRepository
import com.brh.poc.pokemon.ui.model.PokemonUI

class PokemonPagingSource(val repo: PokemonRepository): PagingSource<Int, PokemonUI>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonUI>) = (state.pages.size * PAGING_SIZE)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonUI> {
        val page = params.key ?: 0
        return try {
            val response = repo.getPokemonList(page)
            with(response) {
                LoadResult.Page(list, previousPage, nextPage)
            }
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}