package com.jmat.settings.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.base.extensions.setupToolbar
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.modules.settings.SettingsModuleDependencies
import com.jmat.powertools.modules.showcase.DEEPLINK_SHOWCASE
import com.jmat.settings.R
import com.jmat.settings.databinding.FragmentSettingsLandingBinding
import com.jmat.settings.di.DaggerSettingsComponent
import com.jmat.settings.ui.viewmodel.SettingsLandingViewModel
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SettingsLandingFragment : Fragment(R.layout.fragment_settings_landing) {
    private val binding: FragmentSettingsLandingBinding by viewBinding(
        FragmentSettingsLandingBinding::bind
    )

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
            navigationMode = NavigationMode.CLOSE
        )

        binding.navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.clearData -> {
                    viewModel.clearData()
                    Toast.makeText(requireContext(), "App data cleared", Toast.LENGTH_SHORT).show()
                }
                R.id.showcase -> {
                    requireActivity().navigateDeeplink(DEEPLINK_SHOWCASE)
                }
                R.id.debug -> {
                    findNavController().navigate(
                        R.id.settingsLandingFragment_to_settingsDebugFragment
                    )
                }
                else -> {}
            }
            true
        }
    }
}