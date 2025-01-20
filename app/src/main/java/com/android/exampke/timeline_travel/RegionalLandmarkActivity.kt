package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class RegionalLandmarkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val region = intent.getIntExtra("region", -1)
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
                            filteredLandmarks = it,
                            region = region
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RegionalLandmarkScreen(modifier: Modifier, filteredLandmarks: List<Landmark>, region: Int) {
    Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

        Row() {
            SectionTitle(R.string.nearby_landmark)
            Text(
                ":",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 50.sp,
            )
            SectionTitle(region)
        }
        filteredLandmarks?.forEach { landmark ->
            TrendLandmark(landmark = landmark)
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}