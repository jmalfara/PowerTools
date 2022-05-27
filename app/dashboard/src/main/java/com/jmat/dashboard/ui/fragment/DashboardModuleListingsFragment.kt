package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardListingsBinding
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.adapter.ModuleListingsAdapter
import com.jmat.dashboard.ui.extensions.setupAppbar
import com.jmat.dashboard.ui.model.TabData
import com.jmat.dashboard.ui.viewmodel.DashboardStoreViewModel
import com.jmat.powertools.base.decoration.MarginItemDecoration
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardModuleListingsFragment : Fragment(R.layout.fragment_dashboard_listings) {
    private val binding: FragmentDashboardListingsBinding by viewBinding(
        FragmentDashboardListingsBinding::bind)

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: DashboardStoreViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DaggerDashboardComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    DashboardModuleDependencies::class.java
                )
            ).build().inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            with(recyclerView) {
                adapter = ModuleListingsAdapter()
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(MarginItemDecoration(30))
            }

            requireActivity().setupAppbar(
                tabs = listOf(
                    TabData(
                        id = R.id.tab_popular,
                        text = getString(R.string.tab_popular)
                    ),
                    TabData(
                        id = R.id.tab_new,
                        text = getString(R.string.tab_new)
                    )
                ),
                onTabSelected = { tab ->
                    when (tab.id) {
                        R.id.tab_popular -> viewModel.showPopular(true)
                        R.id.tab_new -> viewModel.showPopular(false)
                    }
                }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    with(binding) {
                        val list = if (uiState.showingPopular) {
                            uiState.popularModules
                        } else uiState.newModules

                        (recyclerView.adapter as ModuleListingsAdapter).submitList(list)
                        loader.isVisible = uiState.loading
                    }
                }
            }
        }

        if (savedInstanceState == null) {
            viewModel.fetchStoreDetails()
        }
    }
}