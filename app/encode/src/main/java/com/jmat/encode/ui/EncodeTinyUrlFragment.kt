package com.jmat.encode.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeTinyurlBinding
import com.jmat.powertools.base.delegate.viewBinding

class EncodeTinyUrlFragment : Fragment(R.layout.fragment_encode_tinyurl) {
    private val binding: FragmentEncodeTinyurlBinding by viewBinding(FragmentEncodeTinyurlBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().finish()
        }

        binding.addTinyUrl.setOnClickListener {
            findNavController().navigate(
                EncodeTinyUrlFragmentDirections
                    .encodeTinyUrlFragmentToEncodeTinyUrlCreateFragment()
            )
        }
    }
}