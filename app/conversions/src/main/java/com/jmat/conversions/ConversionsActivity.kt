package com.jmat.conversions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jmat.conversions.R
import com.jmat.conversions.ui.fragment.ConversionKilometersToMilesFragment
import com.jmat.conversions.ui.fragment.ConversionLiters100KmToMPGFragment
import com.jmat.conversions.ui.fragment.ConversionMilliliterToOunceFragment
import com.jmat.conversions.ui.fragment.ConversionsLandingFragment
import com.jmat.powertools.modules.conversions.*
import java.lang.RuntimeException

class ConversionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversions)
    }
}