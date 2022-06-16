package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardFavouritesBinding
import com.jmat.dashboard.databinding.FragmentDashboardInstalledBinding
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.adapter.FavouritesAdapter
import com.jmat.dashboard.ui.adapter.InstalledAdapter
import com.jmat.dashboard.ui.extensions.setupTabs
import com.jmat.dashboard.ui.model.TabData
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.decoration.MarginItemDecoration
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardInstalledFragment : Fragment(R.layout.fragment_dashboard_installed) {
    private val binding: FragmentDashboardInstalledBinding by viewBinding(
        FragmentDashboardInstalledBinding::bind
    )

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerDashboardComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    DashboardModuleDependencies::class.java
                )
            ).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val installedAdapter = InstalledAdapter()
        with(binding.recyclerView) {
            adapter = installedAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(30))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.installedModules.collect { modules ->
                    installedAdapter.submitList(modules)
                    binding.emptyCard.isVisible = modules.isEmpty()
                }
            }
        }
    }
}