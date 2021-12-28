package com.jmat.settings.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.setupToolbar
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.modules.settings.SettingsModuleDependencies
import com.jmat.settings.R
import com.jmat.settings.databinding.FragmentSettingsLandingBinding
import com.jmat.settings.di.DaggerSettingsComponent
import com.jmat.settings.ui.viewmodel.SettingsLandingViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SettingsLandingFragment : Fragment(R.layout.fragment_settings_landing) {
    private val binding: FragmentSettingsLandingBinding by viewBinding(FragmentSettingsLandingBinding::bind)

    @Inject
    lateinit var viewModelFactory: InjectedViewModelFactory
    private val viewModel: SettingsLandingViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSettingsComponent.builder().appDependencies(
            EntryPointAccessors.fromApplication(
                requireContext(),
                SettingsModuleDependencies::class.java
            )
        ).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            toolbar = binding.toolbar,
            navigationMode = NavigationMode.BACK
        )

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.clearData -> viewModel.clearData()
                else -> {}
            }
            true
        }
    }
}