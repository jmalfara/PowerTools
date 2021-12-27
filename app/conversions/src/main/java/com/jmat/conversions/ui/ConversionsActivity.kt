package com.jmat.conversions.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmat.conversions.R
import com.jmat.conversions.ui.fragment.ConversionKilometersToMilesFragment
import com.jmat.conversions.ui.fragment.ConversionLiters100KmToMPGFragment
import com.jmat.conversions.ui.fragment.ConversionMilliliterToOunceFragment
import com.jmat.powertools.modules.conversions.*
import java.lang.RuntimeException

class ConversionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversions)

        val fragment = when (val deeplinkUrl = intent.dataString) {
            DEEPLINK_CONVERSIONS_L100KM_TO_MPG -> ConversionLiters100KmToMPGFragment()
            DEEPLINK_CONVERSIONS_KM_TO_MILES -> ConversionKilometersToMilesFragment()
            DEEPLINK_CONVERSIONS_ML_TO_OUNCES -> ConversionMilliliterToOunceFragment()
            else -> throw RuntimeException("Unknown Action: $deeplinkUrl")
        }

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}