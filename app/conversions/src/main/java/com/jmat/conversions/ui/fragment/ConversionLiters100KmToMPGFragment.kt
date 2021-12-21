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
import com.jmat.conversions.databinding.FragmentConversionL100kmToMpgBinding
import com.jmat.conversions.ui.model.ConversionEvent
import com.jmat.conversions.ui.viewmodel.ConversionL100KmToMPGViewModel
import com.jmat.powertools.base.textwatchers.NumberFormattingTextWatcher
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.addFocusedOnTextChangeListener
import com.jmat.powertools.base.extensions.showEndIconOnFocus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal

class ConversionLiters100KmToMPGFragment : Fragment(R.layout.fragment_conversion_l100km_to_mpg) {
    private val binding: FragmentConversionL100kmToMpgBinding by viewBinding(
        FragmentConversionL100kmToMpgBinding::bind
    )

    private val viewModel: ConversionL100KmToMPGViewModel by viewModels()

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
                viewModel.calculateMpg(amount)
            }

            toAmount.showEndIconOnFocus()
            toAmount.editText?.addTextChangedListener(NumberFormattingTextWatcher())
            toAmount.editText?.addFocusedOnTextChangeListener { s ->
                val amount = s.toString().takeIf { it.isEmpty().not() }?.toBigDecimal() ?: BigDecimal.ZERO
                viewModel.calculateL100Km(amount)
            }

            useUsGallonSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setUseUsGallon(isChecked)
            }
        }
    }
}