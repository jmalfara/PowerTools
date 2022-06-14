package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.textwatchers.FourDigitCardFormattingTextWatcher
import com.jmat.powertools.base.textwatchers.NumberFormattingTextWatcher
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

            numberFormatter.editText?.addTextChangedListener(NumberFormattingTextWatcher())

            fourDigitCardFormatter.editText?.addTextChangedListener(FourDigitCardFormattingTextWatcher())
        }
    }
}