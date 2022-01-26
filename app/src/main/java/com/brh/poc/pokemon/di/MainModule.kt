package com.brh.poc.pokemon.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.brh.poc.pokemon.repository.network.PokemonApi
import com.brh.poc.pokemon.repository.network.PokemonRepository
import com.brh.poc.pokemon.repository.network.PokemonRepositoryImpl
import com.brh.poc.pokemon.ui.PokemonViewModel
import com.brh.poc.pokemon.ui.model.PokemonUI
import com.brh.poc.pokemon.ui.paging.PokemonPagingSource
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val PAGING_SIZE = 20

private const val BASE_URL = "http://pokeapi.co/api/v2/"
val mainModule = module {

    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single{
        Moshi.Builder().build()
    }

    single {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)

        val retrofit = builder
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()

        retrofit.create(PokemonApi::class.java)
    }

    single<PokemonRepository> {
        PokemonRepositoryImpl(get())
    }

    single<PagingSource<Int, PokemonUI>>  {
        PokemonPagingSource(get())
    }

    single<Pager<Int, PokemonUI>> {
        Pager(PagingConfig(PAGING_SIZE, enablePlaceholders = false)){
            get()
        }
    }

    viewModel { PokemonViewModel(get(), get()) }

}