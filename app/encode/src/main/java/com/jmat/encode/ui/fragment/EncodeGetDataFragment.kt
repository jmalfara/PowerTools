package com.jmat.encode.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeGetDataBinding
import com.jmat.encode.di.DaggerEncodeComponent
import com.jmat.encode.ui.viewmodel.EncodeDownloadImageBytesViewModel
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EncodeGetDataFragment : Fragment(R.layout.fragment_encode_get_data) {
    private val binding: FragmentEncodeGetDataBinding by viewBinding(FragmentEncodeGetDataBinding::bind)

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: EncodeDownloadImageBytesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerEncodeComponent.builder().appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                EncodeModuleDependencies::class.java
            )
        ).build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            submit.setOnClickListener {
                viewModel.downloadImageBytes(binding.urlInput.editText?.text.toString())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    with(binding) {
                        bytes.text = uiState.data
                    }
                }
            }
        }
    }
}