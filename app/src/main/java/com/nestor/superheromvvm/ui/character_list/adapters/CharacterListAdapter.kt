package com.nestor.superheromvvm.ui.character_list.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.databinding.CharacterListItemBinding

private const val TAG = "CharacterListAdapter"

class CharacterListAdapter :
    PagingDataAdapter<CharacterDataWrapper.CharacterDataContainer.Character, CharacterListAdapter.ViewHolder>(
        MyDiffCalback
    ) {

    class ViewHolder(
        val binding: CharacterListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val mShimmer: Shimmer by lazy {
            Shimmer.ColorHighlightBuilder()
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build()
        }
        val mShimmerDrawable: Drawable by lazy {
            ShimmerDrawable().apply { setShimmer(mShimmer) }
        }

        fun bind(item: CharacterDataWrapper.CharacterDataContainer.Character?) {
            with(binding) {
                item?.let {
                    shimmer.hideShimmer()
                    tvName.text = it.name
                    Glide.with(ivPoster)
                        .load("${it.thumbnail.path}.${it.thumbnail.extension}")
                        //.load(loadingPlaceholder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(mShimmerDrawable)
                        .into(ivPoster)
                } ?: {
                    shimmer.startShimmer()
                    tvName.text = "Loading"
                    Glide.with(ivPoster).clear(ivPoster)
                }
            }
        }
    }

    object MyDiffCalback :
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
        Log.i(TAG, "onBindViewHolder: is null? ${item == null}")
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }
}