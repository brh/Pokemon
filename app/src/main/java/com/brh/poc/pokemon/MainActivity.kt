package com.brh.poc.pokemon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brh.poc.pokemon.ui.PokemonViewModel
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PokemonViewModel by viewModel(
        owner = { ViewModelOwner(viewModelStore) }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getPokemonList()
    }
}