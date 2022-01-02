package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.showcase.R
import com.jmat.showcase.databinding.FragmentShowcaseButtonsBinding

class ShowcaseButtonsFragment : Fragment(R.layout.fragment_showcase_buttons) {
    private val binding: FragmentShowcaseButtonsBinding by viewBinding(FragmentShowcaseButtonsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}