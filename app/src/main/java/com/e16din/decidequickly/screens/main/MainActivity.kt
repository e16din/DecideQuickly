package com.e16din.decidequickly.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.e16din.decidequickly.R

class MainActivity : AppCompatActivity() {

    object DATA {
        const val DECISION = "DECISION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
