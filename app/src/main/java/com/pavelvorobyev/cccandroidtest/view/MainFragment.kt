package com.pavelvorobyev.cccandroidtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pavelvorobyev.cccandroidtest.R
import com.pavelvorobyev.cccandroidtest.databinding.FragmentMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(requireView())
        observeViewModel()
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getEstimate()
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(requireActivity(), { viewState ->
            when (viewState) {
                is MainViewModel.ViewState.Data -> {
                    binding.estimate = viewState.data.estimate
                    binding.requester = viewState.data.createdBy
                    binding.creator = viewState.data.createdBy
                    binding.contact = viewState.data.contact
                }
                else -> println("Not implemented")
            }
        })
    }

}