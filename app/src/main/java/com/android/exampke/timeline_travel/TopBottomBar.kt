package com.android.exampke.timeline_travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFE2E2E2),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) { Text("APP NAME") }
        }
    )
}

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Color(0xFFE2E2E2), // Optional: Set the background color
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, // Space icons evenly
                verticalAlignment = Alignment.CenterVertically // Align icons vertically
            ) {
                IconButton(onClick = { /* Home action */ }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_home),
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = { /* Favorite action */ }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_favorite),
                        tint = Color.Unspecified, // Keep original color
                        contentDescription = "Favorite"
                    )
                }
                IconButton(onClick = { /* Camera action */ }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_camera),
                        contentDescription = "Camera"
                    )
                }
                IconButton(onClick = { /* Map action */ }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_map),
                        tint = Color.Unspecified,
                        contentDescription = "Map"
                    )
                }
                IconButton(onClick = { /* Language action */ }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_language),
                        tint = Color.Unspecified,
                        contentDescription = "Language"
                    )
                }
            }
        }
    )
}

