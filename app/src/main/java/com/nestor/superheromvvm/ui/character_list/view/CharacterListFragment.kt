package com.nestor.superheromvvm.ui.character_list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.nestor.superheromvvm.databinding.FragmentCharacterListBinding
import com.nestor.superheromvvm.ui.character_list.adapters.CharacterListAdapter
import com.nestor.superheromvvm.ui.character_list.adapters.CharacterListLoadAdapter
import com.nestor.superheromvvm.ui.character_list.viewmodel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    lateinit var mBinding: FragmentCharacterListBinding
    private val viewModel: CharacterListViewModel by viewModels()
    lateinit var mCharacterAdapter: CharacterListAdapter
    lateinit var mCharacterLoadAdapter: CharacterListLoadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCharacterAdapter = CharacterListAdapter(onClick = { navToCharacterDetails(it) }).apply {
            addLoadStateListener {
                mBinding.layoutRetry.isVisible = it.refresh is LoadState.Error
                mBinding.progressCircular.isVisible = it.refresh is LoadState.Loading
                mBinding.tvNoInfo.isVisible =
                    it.refresh is LoadState.NotLoading && mCharacterAdapter.itemCount == 0
            }
        }
        mCharacterLoadAdapter = CharacterListLoadAdapter(retry = mCharacterAdapter::retry)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCharacterListBinding.inflate(inflater, container, false)
        mBinding.rvCharacter.adapter = mCharacterAdapter.withLoadStateFooter(mCharacterLoadAdapter)
        mBinding.btnRetry.setOnClickListener { mCharacterAdapter.retry() }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.characters.collectLatest {
                mCharacterAdapter.submitData(it)
            }
        }
    }

    private fun navToCharacterDetails(characterId: Int) {
        val action =
            CharacterListFragmentDirections
                .actionCharacterListFragmentToCharacterDetailsFragment(characterId)
        findNavController().navigate(action)
    }
}
