package com.nestor.superheromvvm.ui.character_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerDrawable
import com.nestor.superheromvvm.R
import com.nestor.superheromvvm.data.model.CharacterExtraData
import com.nestor.superheromvvm.databinding.CharacterExtraDataBinding
import com.nestor.superheromvvm.util.appShimmer


class CharacterExtraDataAdapter :
    PagingDataAdapter<CharacterExtraData, CharacterExtraDataAdapter.ViewHolder>(MyDiffCallback) {
    class ViewHolder(val mBinding: CharacterExtraDataBinding) :
        RecyclerView.ViewHolder(mBinding.root) {
        val mShimmerDrawable = ShimmerDrawable().apply { setShimmer(appShimmer) }
        fun bind(item: CharacterExtraData?) {
            with(mBinding) {
                item?.let {
                    tvLink.text = it.name
                    Glide.with(ivPoster)
                        .load(it.thumbnail)
                        .centerCrop()
                        .placeholder(mShimmerDrawable)
                        .error(ivPoster.context.getDrawable(R.drawable.ic_baseline_image_not_supported_24))
                        .into(ivPoster)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterExtraDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
        )
    }

    object MyDiffCallback : DiffUtil.ItemCallback<CharacterExtraData>() {
        override fun areItemsTheSame(
            oldItem: CharacterExtraData,
            newItem: CharacterExtraData
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CharacterExtraData,
            newItem: CharacterExtraData
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}