package com.jmat.encode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jmat.encode.di.DaggerEncodeComponent
import com.jmat.powertools.modules.encode.EncodeModuleDependencies
import dagger.hilt.android.EntryPointAccessors

class EncodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encode)
    }
}