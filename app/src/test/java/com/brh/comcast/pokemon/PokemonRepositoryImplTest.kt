package com.brh.poc.pokemon

import com.brh.poc.pokemon.repository.network.PokemonApi
import com.brh.poc.pokemon.repository.network.PokemonRepository
import com.brh.poc.pokemon.repository.network.PokemonRepositoryImpl
import org.junit.Test

import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class PokemonRepositoryImplTest: KoinTest {

    val apiMock: PokemonApi = mock()
    val repo: PokemonRepository by inject()

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single<PokemonApi>{
                        apiMock
                    }
                    single<PokemonRepository> {
                        PokemonRepositoryImpl(get())
                    }
                }
            )
        }
    }

    @Test
    fun listFetch() {
    }
}