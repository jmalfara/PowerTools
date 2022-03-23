package com.jmat.encode.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeTinyurlCreateBinding
import com.jmat.encode.di.DaggerEncodeComponent
import com.jmat.encode.ui.viewmodel.EncodeTinyUrlCreateViewModel
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jmat.encode.ui.viewmodel.EncodeTinyUrlCreateViewModel.Event.*

class EncodeTinyUrlCreateFragment : DialogFragment(R.layout.fragment_encode_tinyurl_create) {
    private val binding: FragmentEncodeTinyurlCreateBinding by viewBinding(FragmentEncodeTinyurlCreateBinding::bind)

    @Inject
    lateinit var factory: InjectedViewModelFactory
    private val viewModel: EncodeTinyUrlCreateViewModel by viewModels { factory }

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
            cancel.setOnClickListener {
                dismiss()
            }

            submit.setOnClickListener {
                viewModel.createTinyUrl(binding.urlInput.editText?.text.toString())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        binding.submit.isEnabled = uiState.loading.not()
                    }
                }

                launch {
                    viewModel.event.collect { event ->
                        when (event) {
                            is UrlCreated -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Url Created",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            is Error -> {
                                Toast.makeText(
                                    requireContext(),
                                    event.exception.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}