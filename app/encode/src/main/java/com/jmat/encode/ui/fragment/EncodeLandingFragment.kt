package com.jmat.encode.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeLandingBinding
import com.jmat.powertools.base.delegate.viewBinding

class EncodeLandingFragment : Fragment(R.layout.fragment_encode_landing) {
    private val binding: FragmentEncodeLandingBinding by viewBinding(FragmentEncodeLandingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setNavigationOnClickListener {
                requireActivity().finish()
            }

            tinyUrl.setOnClickListener {
                findNavController().navigate(
                    EncodeLandingFragmentDirections.encodeLandingFragmentToEncodeTinyUrlFragment()
                )
            }
        }
    }
}