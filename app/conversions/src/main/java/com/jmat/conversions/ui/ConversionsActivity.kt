package com.jmat.conversions.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.jmat.conversions.R
import com.jmat.conversions.ui.fragment.ConversionKilometersToMilesFragment
import com.jmat.conversions.ui.fragment.ConversionLiters100KmToMPGFragment
import com.jmat.conversions.ui.fragment.ConversionMilliliterToOunceFragment
import com.jmat.powertools.base.extensions.NavigationMode
import com.jmat.powertools.base.extensions.setupSupportActionbar
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KEY
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KM_TO_MILES
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_KM_TO_MPG
import com.jmat.powertools.modules.conversions.CONVERSIONS_ACTION_MILLILITERS_TO_OUNCES
import java.lang.RuntimeException

class ConversionsActivity : AppCompatActivity() {
//    private val action: String by lazy {
//        intent.getStringExtra(CONVERSIONS_ACTION_KEY)!!
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversions)

        setupSupportActionbar(
            toolbar = findViewById(R.id.toolbar),
            title = getString(R.string.conversion_title),
            navigationMode = NavigationMode.BACK
        )

//        val fragment = when(CONVERSIONS_ACTION_KM_TO_MPG) {
//            CONVERSIONS_ACTION_KM_TO_MPG -> ConversionLiters100KmToMPGFragment()
//            CONVERSIONS_ACTION_KM_TO_MILES -> ConversionKilometersToMilesFragment()
//            CONVERSIONS_ACTION_MILLILITERS_TO_OUNCES -> ConversionMilliliterToOunceFragment()
////            else -> throw RuntimeException("Unknown Action: $action")
//        }

        val fragment = ConversionLiters100KmToMPGFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_feature, menu)
        return true
    }
}