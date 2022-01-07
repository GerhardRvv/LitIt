package com.gerhardrvv.litit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.gerhardrvv.litit.ui.LitItApp
import com.gerhardrvv.litit.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainViewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LitItApp(mainViewModel)
        }
    }
}
