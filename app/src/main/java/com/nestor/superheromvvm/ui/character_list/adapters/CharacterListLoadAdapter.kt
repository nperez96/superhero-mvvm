package com.nestor.superheromvvm.ui.character_list.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nestor.superheromvvm.databinding.CharacterListLoadingBinding

class CharacterListLoadAdapter(val retry: () -> Unit) :
    LoadStateAdapter<CharacterListLoadAdapter.CharacterListLoadViewHolder>() {

    class CharacterListLoadViewHolder(
        retry: () -> Unit, private val binding: CharacterListLoadingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(state: LoadState) {
            binding.spinner.visibility =
                if (state is LoadState.Loading) View.VISIBLE else View.GONE
            binding.layoutRetry.visibility =
                if (state is LoadState.Error) View.VISIBLE else View.GONE
        }
    }

    override fun onBindViewHolder(holder: CharacterListLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharacterListLoadViewHolder {
        val binding = CharacterListLoadingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterListLoadViewHolder(retry = retry, binding = binding)
    }
}