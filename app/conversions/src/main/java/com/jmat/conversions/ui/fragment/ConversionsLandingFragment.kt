package com.jmat.conversions.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmat.conversions.R
import com.jmat.conversions.databinding.FragmentConversionsLandingBinding
import com.jmat.powertools.base.delegate.viewBinding
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.setupToolbar

class ConversionsLandingFragment : Fragment(R.layout.fragment_conversions_landing) {
    private val binding: FragmentConversionsLandingBinding by viewBinding(FragmentConversionsLandingBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            setupToolbar(
                toolbar = toolbar,
                navigationMode = NavigationMode.CLOSE
            )

            kilometersToMiles.setOnClickListener {
                findNavController().navigate(
                    R.id.conversionLandingFragment_to_conversionKilometersToMilesFragment
                )
            }

            l100kmToMPG.setOnClickListener {
                findNavController().navigate(
                    R.id.conversionLandingFragment_to_conversionLiters100KmToMPGFragment
                )
            }

            millitersToOunces.setOnClickListener {
                findNavController().navigate(
                    R.id.conversionLandingFragment_to_conversionMillilitersToOunceFragment
                )
            }
        }
    }
}