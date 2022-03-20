package com.jmat.encode.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.jmat.encode.R
import com.jmat.encode.databinding.FragmentEncodeTinyurlCreateBinding
import com.jmat.powertools.base.delegate.viewBinding

class EncodeTinyUrlCreateFragment : DialogFragment(R.layout.fragment_encode_tinyurl_create) {
    private val binding: FragmentEncodeTinyurlCreateBinding by viewBinding(FragmentEncodeTinyurlCreateBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cancel.setOnClickListener {
                dismiss()
            }

            submit.setOnClickListener {
                Toast.makeText(requireContext(), "Submit URL", Toast.LENGTH_LONG).show()
            }
        }
    }
}