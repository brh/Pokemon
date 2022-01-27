package com.brh.poc.pokemon.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.brh.poc.pokemon.databinding.FragmentPokemonListBinding
import com.brh.poc.pokemon.ui.adapter.PokemonDiffUtil
import com.brh.poc.pokemon.ui.adapter.ViewBindingPagingAdapter
import com.brh.poc.pokemon.ui.model.PokemonUI
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListFragment : Fragment() {
    private val viewModel: PokemonViewModel by viewModel(
        owner = { ViewModelOwner(viewModelStore) }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPokemonListBinding.inflate(inflater).apply {
            rvPokemon.layoutManager = LinearLayoutManager(root.context)
            val adapter = ViewBindingPagingAdapter<PokemonUI>(lifecycleOwner, PokemonDiffUtil())
            rvPokemon.adapter = adapter

            lifecycleScope.launch {
                viewModel.pagerFlow.collectLatest { data ->
                    adapter.submitData(data)
                }
            }

        }.root
    }
}