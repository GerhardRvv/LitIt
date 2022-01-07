package com.gerhardrvv.litit.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gerhardrvv.litit.ui.navigation.LitItNavHost
import com.gerhardrvv.litit.ui.theme.LitItTheme
import com.gerhardrvv.litit.viewmodel.MainViewModel
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun LitItApp(mainViewModel: MainViewModel) {
    ProvideWindowInsets {
        LitItTheme {
            Scaffold(modifier = Modifier.fillMaxHeight()) {
                LitItNavHost(mainViewModel)
            }
        }
    }
}
