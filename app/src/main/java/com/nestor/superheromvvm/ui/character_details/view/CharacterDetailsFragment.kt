package com.nestor.superheromvvm.ui.character_details.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerDrawable
import com.nestor.superheromvvm.R
import com.nestor.superheromvvm.data.model.CharacterDataWrapper
import com.nestor.superheromvvm.databinding.FragmentCharacterDetailsBinding
import com.nestor.superheromvvm.ui.character_details.adapter.CharacterExtraDataAdapter
import com.nestor.superheromvvm.ui.character_details.view_model.CharacterDetailsViewModel
import com.nestor.superheromvvm.util.appShimmer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "CharacterDetailsFragmen"

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {
    private val mShimmerDrawable = ShimmerDrawable().apply { setShimmer(appShimmer) }
    private lateinit var mBinding: FragmentCharacterDetailsBinding
    private val viewModel: CharacterDetailsViewModel by activityViewModels()
    private val mArguments: CharacterDetailsFragmentArgs by navArgs()
    lateinit var mComicsAdapter: CharacterExtraDataAdapter
    lateinit var mSeriesAdapter: CharacterExtraDataAdapter
    lateinit var mStoriesAdapter: CharacterExtraDataAdapter
    lateinit var mEventsAdapter: CharacterExtraDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mComicsAdapter = CharacterExtraDataAdapter().apply {
            addLoadStateListener {
                handleLoadState(
                    it,
                    tvEmpty = mBinding.tvComicsEmpty,
                    adapter = this
                )
            }
        }
        mSeriesAdapter = CharacterExtraDataAdapter()
            .apply {
                addLoadStateListener {
                    handleLoadState(
                        it,
                        tvEmpty = mBinding.tvSeriesEmpty,
                        adapter = this
                    )
                }
            }
        mStoriesAdapter = CharacterExtraDataAdapter()
            .apply {
                addLoadStateListener {
                    handleLoadState(
                        it,
                        tvEmpty = mBinding.tvStoresEmpty,
                        adapter = this
                    )
                }
            }
        mEventsAdapter = CharacterExtraDataAdapter()
            .apply {
                addLoadStateListener {
                    handleLoadState(
                        it,
                        tvEmpty = mBinding.tvEventsEmpty,
                        adapter = this
                    )
                }
            }
    }

    private fun handleLoadState(
        state: CombinedLoadStates,
        tvEmpty: TextView,
        adapter: CharacterExtraDataAdapter
    ) {
        tvEmpty.visibility =
            if (state.refresh is LoadState.NotLoading && adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentCharacterDetailsBinding.inflate(
            LayoutInflater.from(requireContext()),
            container,
            false
        )
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvComics.adapter = mComicsAdapter
        mBinding.rvEvents.adapter = mEventsAdapter
        mBinding.rvStories.adapter = mStoriesAdapter
        mBinding.rvSeries.adapter = mSeriesAdapter
        viewModel.setCharacterId(mArguments.characterId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.comics.collect {
                mComicsAdapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.series.collect {
                mSeriesAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events.collect {
                mEventsAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stories.collect {
                mStoriesAdapter.submitData(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.character.collect {
                updateUi(it)
            }
        }
    }

    private fun updateUi(response: Response<CharacterDataWrapper.CharacterDataContainer.Character>) {
        with(mBinding) {
            shimmerLayout.stopShimmer()
            shimmerLayout.hideShimmer()
            resetUiColors()
            response.body()?.let {
                tvCharacterName.text = it.name
                (activity as AppCompatActivity).supportActionBar!!.title =
                    getString(R.string.character_name).format(it.name)
                Glide.with(requireContext())
                    .load("${it.thumbnail.path}.${it.thumbnail.extension}")
                    .fitCenter()
                    .placeholder(mShimmerDrawable)
                    .into(ivPoster)
                if (it.description.isNullOrBlank()) {
                    tvCharacterDesc.text = getString(R.string.no_description)
                } else {
                    tvCharacterDesc.text = it.description
                }
            }
            if (response.code() != 200) {
                tvCharacterName.text = "${response.code()}: ${response.message()}"
            }
        }
    }

    private fun resetUiColors() {
        with(mBinding) {
            ivPoster.setBackgroundColor(0)
            tvCharacterDesc.setBackgroundColor(0)
            tvCharacterName.setBackgroundColor(0)
            comicsSection.setBackgroundColor(0)
            seriesSection.setBackgroundColor(0)
            eventsSection.setBackgroundColor(0)
            storiesSection.setBackgroundColor(0)
        }
    }
}