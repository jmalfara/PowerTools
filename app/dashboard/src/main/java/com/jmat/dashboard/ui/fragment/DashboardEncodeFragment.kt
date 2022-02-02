package com.jmat.dashboard.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.jmat.dashboard.R
import com.jmat.dashboard.databinding.FragmentDashboardEncodeBinding
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.navigateDeeplink
import com.jmat.powertools.modules.encode.DEEPLINK_ENCODE_TINYURL

class DashboardEncodeFragment : Fragment(R.layout.fragment_dashboard_encode) {
    private val binding: FragmentDashboardEncodeBinding by viewBinding(FragmentDashboardEncodeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tinyUrl.setOnClickListener {
            requireContext().navigateDeeplink(
                DEEPLINK_ENCODE_TINYURL
            )
        }
    }
}