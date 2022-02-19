package com.nestor.superheromvvm.ui.character_list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nestor.superheromvvm.databinding.FragmentCharacterListBinding
import com.nestor.superheromvvm.ui.character_list.adapters.CharacterListAdapter
import com.nestor.superheromvvm.ui.character_list.viewmodel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {
    lateinit var mBinding: FragmentCharacterListBinding
    private val viewModel: CharacterListViewModel by viewModels()
    private lateinit var mCharacterAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentCharacterListBinding.inflate(inflater, container, false)
        mCharacterAdapter = CharacterListAdapter()
        mBinding.rvCharacter.adapter = mCharacterAdapter
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCharacters().collectLatest { mCharacterAdapter.submitData(it) }
        }
    }
}