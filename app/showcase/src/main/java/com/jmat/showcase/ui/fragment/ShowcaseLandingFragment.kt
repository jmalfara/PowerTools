package com.jmat.showcase.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.showcase.R
import com.jmat.showcase.databinding.FragmentShowcaseLandingBinding

class ShowcaseLandingFragment : Fragment(R.layout.fragment_showcase_landing) {
    private val binding: FragmentShowcaseLandingBinding by viewBinding(FragmentShowcaseLandingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().finish()
            }

            buttonsAction.setOnClickListener {
                findNavController().navigate(R.id.showcaseLandingFragment_to_showcaseButtonsFragment)
            }

            typographyAction.setOnClickListener {
                findNavController().navigate(R.id.showcaseLandingFragment_to_showcaseTextFragment)
            }

            textInputAction.setOnClickListener {
                findNavController().navigate(R.id.showcaseLandingFragment_to_showcaseTextInputFragment)
            }

            textInputComposeAction.setOnClickListener {
                findNavController().navigate(R.id.showcaseLandingFragment_to_showcaseTextInputComposeFragment)
            }
        }
    }
}