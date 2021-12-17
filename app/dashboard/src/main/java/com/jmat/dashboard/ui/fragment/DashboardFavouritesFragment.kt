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
import com.jmat.dashboard.ui.adapter.FavouritesAdapter
import com.jmat.dashboard.ui.viewmodel.DashboardViewModel
import com.jmat.dashboard.ui.viewmodel.DashboardViewModelFactory
import com.jmat.powertools.base.decoration.MarginItemDecoration
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.data.preferences.userPreferencesStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DashboardFavouritesFragment : Fragment(R.layout.fragment_dashboard_favourites) {
    private val binding: FragmentDashboardFavouritesBinding by viewBinding(
        FragmentDashboardFavouritesBinding::bind
    )

    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(
            userPreferencesRepository = UserPreferencesRepository(
                dataStore = requireContext().userPreferencesStore
            )
        )
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
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.favourites.collect { favourites ->
                    favouritesAdapter.submitList(favourites)
                    binding.emptyCard.isVisible = favourites.isEmpty()
                }
            }
        }
    }
}