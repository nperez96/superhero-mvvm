package com.nestor.superheromvvm.ui.character_list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerDrawable
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.databinding.CharacterListItemBinding
import com.nestor.superheromvvm.util.appShimmer

class CharacterListAdapter(val onClick: (characterId: Int) -> Unit) :
    PagingDataAdapter<CharacterDataWrapper.CharacterDataContainer.Character, CharacterListAdapter.ViewHolder>(
        MyDiffCalback()
    ) {

    class ViewHolder(
        val binding: CharacterListItemBinding,
        val onClick: (characterId: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        val mShimmerDrawable = ShimmerDrawable().apply { setShimmer(appShimmer) }

        fun bind(item: CharacterDataWrapper.CharacterDataContainer.Character?) {
            with(binding) {
                item?.let {
                    tvName.text = it.name
                    binding.root.setOnClickListener { _ -> onClick(it.id) }
                    Glide.with(ivPoster)
                        .load("${it.thumbnail.path}.${it.thumbnail.extension}")
                        .placeholder(mShimmerDrawable)
                        .into(ivPoster)
                }
            }
        }

    }

    class MyDiffCalback :
        DiffUtil.ItemCallback<CharacterDataWrapper.CharacterDataContainer.Character>() {
        override fun areItemsTheSame(
            oldItem: CharacterDataWrapper.CharacterDataContainer.Character,
            newItem: CharacterDataWrapper.CharacterDataContainer.Character
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterDataWrapper.CharacterDataContainer.Character,
            newItem: CharacterDataWrapper.CharacterDataContainer.Character
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick = onClick
        )
    }

}