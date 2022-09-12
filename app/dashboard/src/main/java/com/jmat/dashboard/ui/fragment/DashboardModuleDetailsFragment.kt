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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardModuleDetailsBinding
import com.jmat.dashboard.di.DaggerDashboardComponent
import com.jmat.dashboard.ui.DashboardModuleDetailsActivityArgs
import com.jmat.dashboard.ui.viewmodel.DashboardModuleDetailsViewModel
import com.jmat.powertools.base.data.getText
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.di.InjectedSavedStateViewModelFactory
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
    private val viewModel: DashboardModuleDetailsViewModel by viewModels {
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
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            name.text = args.module.name
            description.text = args.module.shortDescription
            author.text = args.module.author

            Glide.with(requireActivity())
                .load(args.module.iconUrl)
                .fitCenter()
                .into(icon)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest { uiState ->
                    with(binding) {
                        action.text = getString(uiState.actionTextRes)
                        action.isEnabled = uiState.installing.not()

                        if (uiState.installed) {
                            action.text = getString(R.string.dashboard_details_uninstall)
                            action.setOnClickListener {
                                viewModel.uninstallModule(args.module)
                            }
                        } else {
                            action.text = getString(R.string.dashboard_details_install)
                            action.setOnClickListener {
                                viewModel.installModule(args.module)
                            }
                        }

                        uiState.notificationMessage?.let {
                            Toast.makeText(requireContext(), getText(it), Toast.LENGTH_LONG).show()
                            viewModel.consumeNotificationMessage()
                        }
                    }
                }
            }
        }
    }
}