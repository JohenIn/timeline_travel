package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class RegionalLandmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filteredLandmarks = intent.getParcelableArrayListExtra<Landmark>("filteredLandmarks")

        setContent {
            Timeline_travelTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    filteredLandmarks?.let {
                        RegionalLandmarkScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(Color.White),
                            filteredLandmarks = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegionalLandmarkScreen(modifier: Modifier, filteredLandmarks: List<Landmark>) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        filteredLandmarks?.forEach { landmark ->
            TrendLandmark(landmark = landmark)
        }
    }
}