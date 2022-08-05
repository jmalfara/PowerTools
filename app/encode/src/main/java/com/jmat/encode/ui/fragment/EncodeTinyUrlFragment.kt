package com.jmat.encode.ui.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeTinyurlBinding
import com.jmat.encode.di.DaggerEncodeComponent
import com.jmat.encode.ui.adapter.EncodeTinyUrlsAdapter
import com.jmat.encode.ui.viewmodel.EncodeTinyUrlViewModel
import com.jmat.powertools.base.decoration.MarginItemDecoration
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.base.list.actionmode.ToolbarActionMode
import com.jmat.powertools.base.list.selection.RecyclerViewSelectionTracker
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import javax.inject.Inject

class EncodeTinyUrlFragment : Fragment(R.layout.fragment_encode_tinyurl) {
    private val binding: FragmentEncodeTinyurlBinding by viewBinding(FragmentEncodeTinyurlBinding::bind)

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: EncodeTinyUrlViewModel by viewModels { viewModelFactory }

    var actionMode: ActionMode? = null

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
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            addTinyUrl.setOnClickListener {
                findNavController().navigate(
                    EncodeTinyUrlFragmentDirections
                        .encodeTinyUrlFragmentToEncodeTinyUrlCreateFragment()
                )
            }
        }

        val adapter = EncodeTinyUrlsAdapter { item ->
            val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
            val clip = ClipData.newPlainText("TinyUrl", item.url)
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireContext(), item.url, Toast.LENGTH_SHORT).show()
        }

        with(binding.tinyUrls) {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(30))
        }

        RecyclerViewSelectionTracker(
            "encode-selection-id",
            recyclerView = binding.tinyUrls,
            selectionAdapter = adapter,
            onStartSelection = { items, clearSelection ->
                actionMode = binding.toolbar.startActionMode(
                    ToolbarActionMode(
                        menuInflater = requireActivity().menuInflater,
                        deleteAction = {
                            viewModel.deleteTinyUrls(
                                urls = items()
                            )
                        },
                        dismissActionMode = {
                            clearSelection()
                        }
                    )
                )
            },
            onFinishSelection = {
                actionMode?.finish()
            },
            onSelectionUpdated = { items ->
                actionMode?.title = getString(R.string.encode_tinyurl_delete_title, items.size)
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.uiState.collect { uiState ->
                        adapter.submitList(uiState.tinyUrls)
                    }
                }
            }
        }
    }
}