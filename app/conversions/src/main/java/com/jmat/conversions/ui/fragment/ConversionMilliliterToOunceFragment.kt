package com.jmat.conversions.ui.fragment

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jmat.conversions.R
import com.jmat.conversions.databinding.FragmentConversionMlToOzBinding
import com.jmat.conversions.di.ConversionsComponent
import com.jmat.conversions.di.ConversionsInjectionInitializer
import com.jmat.conversions.di.InjectionInitializer
import com.jmat.conversions.ui.CONVERSION_MODULE_NAME
import com.jmat.conversions.ui.ID_CONVERSIONS_ML_TO_OUNCES
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.conversions.ui.viewmodel.ConversionShortcutsViewModel
import com.jmat.conversions.ui.viewmodel.ConversionFavouritesViewModelFactory
import com.jmat.conversions.ui.viewmodel.ConversionMilliliterToOunceViewModel
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.addFocusedOnTextChangeListener
import com.jmat.powertools.base.extensions.isRoot
import com.jmat.powertools.base.extensions.setupToolbar
import com.jmat.powertools.base.extensions.showEndIconOnFocus
import com.jmat.powertools.base.extensions.toCleanBigDecimal
import com.jmat.powertools.base.textfieldformatting.decimal.DecimalFormattingTextWatcher
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ConversionMilliliterToOunceFragment : Fragment(R.layout.fragment_conversion_ml_to_oz),
    InjectionInitializer<ConversionsComponent> by ConversionsInjectionInitializer() {

    private val binding: FragmentConversionMlToOzBinding by viewBinding(
        FragmentConversionMlToOzBinding::bind
    )

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    private val shortcutsViewModel: ConversionShortcutsViewModel by viewModels {
        ConversionFavouritesViewModelFactory(
            userPreferencesRepository = userPreferencesRepository,
            featureId = ID_CONVERSIONS_ML_TO_OUNCES,
            moduleName = CONVERSION_MODULE_NAME
        )
    }

    private val viewModel: ConversionMilliliterToOunceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        buildComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar(
            toolbar = binding.toolbar,
            navigationMode = if (findNavController().isRoot()) {
                NavigationMode.CLOSE
            } else NavigationMode.BACK
        ).apply {
            setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.favourite) {
                    shortcutsViewModel.toggleShortcut()
                    true
                } else false
            }
        }

        with(binding) {
            fromAmount.showEndIconOnFocus()
            fromAmount.editText?.addTextChangedListener(
                DecimalFormattingTextWatcher(
                    editText = fromAmount.editText!!
                )
            )
            fromAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().toCleanBigDecimal()
                viewModel.setMilliliters(amount)
            }

            toAmount.showEndIconOnFocus()
            toAmount.editText?.addTextChangedListener(
                DecimalFormattingTextWatcher(
                    editText = toAmount.editText!!
                )
            )
            toAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().toCleanBigDecimal()
                viewModel.setOunces(amount)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    shortcutsViewModel.isShortcut.collect { isShortcut ->
                        val color = if (isShortcut) {
                            ResourcesCompat.getColor(
                                resources,
                                com.jmat.powertools.R.color.favourite,
                                null
                            )
                        } else {
                            val typedValue = TypedValue()
                            val theme = requireContext().theme
                            theme.resolveAttribute(
                                com.google.android.material.R.attr.colorOnPrimary,
                                typedValue,
                                true
                            )
                            typedValue.data
                        }

                        binding.toolbar.menu.findItem(R.id.favourite).icon.setTint(color)
                    }
                }

                launch {
                    viewModel.events.collect { event ->
                        when (event) {
                            is ConversionEvent.UpdateToAmount -> {
                                binding.toAmount.editText?.setText(
                                    event.amount,
                                    TextView.BufferType.EDITABLE
                                )
                            }
                            is ConversionEvent.UpdateFromAmount -> {
                                binding.fromAmount.editText?.setText(
                                    event.amount,
                                    TextView.BufferType.EDITABLE
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}