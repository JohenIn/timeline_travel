package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.android.exampke.timeline_travel.viewmodel.MapViewModel
import com.android.exampke.timeline_travel.viewmodel.ShowGoogleMap

class MapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timeline_travelTheme {
                Scaffold(
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigationBar()
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MapScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MapScreen(modifier: Modifier) {
    ShowGoogleMap(mapViewModel = MapViewModel(), modifier = Modifier
        .fillMaxSize())
}

