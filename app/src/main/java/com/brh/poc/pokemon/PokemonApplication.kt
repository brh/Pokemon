package com.brh.poc.pokemon

import android.app.Application
import com.brh.poc.pokemon.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@PokemonApplication)
            modules(mainModule)
        }

    }

}