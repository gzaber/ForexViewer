package com.gzaber.forexviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gzaber.forexviewer.ui.forexpairs.ForexPairsScreen
import com.gzaber.forexviewer.ui.theme.ForexViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForexViewerTheme {
                ForexPairsScreen()
            }
        }
    }
}