package com.jmat.conversions.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmat.powertools.data.preferences.UserPreferencesRepository
import com.jmat.powertools.data.preferences.userPreferencesStore
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import com.jmat.conversions.R

import com.jmat.conversions.databinding.FragmentConversionKmToMBinding
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.conversions.ui.viewmodel.ConversionKilometersToMilesViewModel
import com.jmat.conversions.ui.viewmodel.ConversionKilometersToMilesViewModelFactory
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.addFocusedOnTextChangeListener
import com.jmat.powertools.base.extensions.showEndIconOnFocus
import com.jmat.powertools.base.textwatchers.NumberFormattingTextWatcher

class ConversionKilometersToMilesFragment : Fragment(R.layout.fragment_conversion_km_to_m) {
    private val binding: FragmentConversionKmToMBinding by viewBinding(
        FragmentConversionKmToMBinding::bind
    )

    private val viewModel: ConversionKilometersToMilesViewModel by viewModels {
        ConversionKilometersToMilesViewModelFactory(
            userPreferencesRepository = UserPreferencesRepository(
                dataStore = requireContext().userPreferencesStore
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                withContext(Dispatchers.Main) {
//                    viewModel.isFavourite.collect { isFavourite ->
//                        val color = if (isFavourite) {
//                            requireContext().resources.getColor(AppR.color.favourite)
//                        } else {
//                            val typedValue = TypedValue()
//                            val theme = requireContext().theme
//                            theme.resolveAttribute(MaterialR.attr.colorOnPrimary, typedValue, true)
//                            typedValue.data
//                        }
//
//                        binding.toolbar.menu.findItem(R.id.favourite).icon.setTint(color)
//                    }
//                }
//            }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.events.collect { event ->
                    when (event) {
                        is ConversionEvent.UpdateToAmount -> {
                            binding.toAmount.editText?.setText(
                                event.amount.toPlainString(),
                                TextView.BufferType.EDITABLE
                            )
                        }
                        is ConversionEvent.UpdateFromAmount -> {
                            binding.fromAmount.editText?.setText(
                                event.amount.toPlainString(),
                                TextView.BufferType.EDITABLE
                            )
                        }
                    }
                }
            }
        }

        with(binding) {
            fromAmount.showEndIconOnFocus()
            fromAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            fromAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount =
                    s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.setKilometers(amount)
            }

            toAmount.showEndIconOnFocus()
            toAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            toAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount =
                    s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.setMiles(amount)
            }
        }
    }
}