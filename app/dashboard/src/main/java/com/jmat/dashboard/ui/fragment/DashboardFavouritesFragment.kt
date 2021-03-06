package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardFavouritesBinding
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.adapter.FavouritesAdapter
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.powertools.base.decoration.MarginItemDecoration
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch

class DashboardFavouritesFragment : Fragment(R.layout.fragment_dashboard_favourites) {
    private val binding: FragmentDashboardFavouritesBinding by viewBinding(
        FragmentDashboardFavouritesBinding::bind
    )
    private val viewModel: DashboardViewModel by activityViewModels()

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

        val favouritesAdapter = FavouritesAdapter()
        with(binding.recyclerView) {
            adapter = favouritesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MarginItemDecoration(30))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { uiState ->
                    favouritesAdapter.submitList(uiState.favouriteFeatures)
                    binding.emptyCard.isVisible = uiState.favouriteFeatures.isEmpty()
                }
            }
        }
    }
}