package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.textfieldformatting.decimal.DecimalFormattingTextWatcher
import com.jmat.powertools.base.textfieldformatting.fourdigitcard.FourDigitCardFormattingTextWatcher
import com.jmat.showcase.R
import com.jmat.showcase.databinding.FragmentShowcaseTextInputBinding

class ShowcaseTextInputFragment : Fragment(R.layout.fragment_showcase_text_input) {
    private val binding: FragmentShowcaseTextInputBinding by viewBinding(FragmentShowcaseTextInputBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }

            numberFormatter.editText?.addTextChangedListener(
                DecimalFormattingTextWatcher(
                    editText = numberFormatter.editText!!
                )
            )

            fourDigitCardFormatter.editText?.addTextChangedListener(
                FourDigitCardFormattingTextWatcher(
                    editText = fourDigitCardFormatter.editText!!
                )
            )
        }
    }
}