package com.jmat.conversions.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jmat.conversions.R
import com.jmat.conversions.databinding.FragmentConversionMlToOzBinding
import com.jmat.powertools.base.textwatchers.NumberFormattingTextWatcher
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.conversions.ui.viewmodel.ConversionMilliliterToOunceViewModel
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.addFocusedOnTextChangeListener
import com.jmat.powertools.base.extensions.showEndIconOnFocus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ConversionMilliliterToOunceFragment : Fragment(R.layout.fragment_conversion_ml_to_oz) {
    private val binding: FragmentConversionMlToOzBinding by viewBinding(
        FragmentConversionMlToOzBinding::bind
    )

    private val viewModel: ConversionMilliliterToOunceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)

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
                val amount = s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.setMilliliters(amount)
            }

            toAmount.showEndIconOnFocus()
            toAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            toAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.setOunces(amount)
            }
        }
    }
}