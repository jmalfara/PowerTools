package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardModuleDetailsBinding
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.DashboardModuleDetailsActivityArgs
import com.jmat.dashboard.ui.viewmodel.DashboardModuleDetailsViewModel
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.AssistedSavedStateViewModelFactory
import com.jmat.powertools.base.di.InjectedSavedStateViewModelFactory
import com.jmat.powertools.base.di.InjectedViewModelFactory
import com.jmat.powertools.modules.dashboard.DashboardModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class DashboardModuleDetailsFragment : Fragment(R.layout.fragment_dashboard_module_details) {
    private val binding: FragmentDashboardModuleDetailsBinding by viewBinding(FragmentDashboardModuleDetailsBinding::bind)
    private val args: DashboardModuleDetailsActivityArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: InjectedSavedStateViewModelFactory
    val viewModel: DashboardModuleDetailsViewModel by viewModels {
        viewModelFactory.create(this, requireActivity().intent.extras)
    }

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
            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            name.text = args.moduleData.module.name
            description.text = args.moduleData.module.shortDescription
            author.text = args.moduleData.module.author

            Glide.with(requireActivity())
                .load(args.moduleData.module.iconUrl)
                .fitCenter()
                .into(icon)

            action.text = if (args.moduleData.installed) {
                "Uninstall"
            } else "Install"

            action.setOnClickListener {
                if (args.moduleData.installed) {
                    Toast.makeText(requireContext(), "Uninstall", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Install", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    with(binding) {
                        action.text = getString(uiState.actionTextRes)

                        if (uiState.installed) {
                            action.isEnabled = false
                            action.setOnClickListener {
                                viewModel.uninstall()
                            }
                        } else {
                            action.isEnabled = true
                            action.setOnClickListener {
                                viewModel.install()
                            }
                        }
                    }
                }
            }
        }
    }
}