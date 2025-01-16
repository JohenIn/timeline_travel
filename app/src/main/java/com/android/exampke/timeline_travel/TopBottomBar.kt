package com.android.exampke.timeline_travel

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


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
    val context = LocalContext.current
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .drawBehind {
                drawLine(color = Color(0x552b2b2b),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx())
            },
        containerColor = Color.White, // Optional: Set the background color
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly, // Space icons evenly
                verticalAlignment = Alignment.CenterVertically // Align icons vertically
            ) {
                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_home),
                        contentDescription = "Home"
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(context, FavoriteActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_favorite),
                        tint = Color.Unspecified, // Keep original color
                        contentDescription = "Favorite"
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(context, CameraActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_camera),
                        contentDescription = "Camera"
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(context, MapActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_map),
                        tint = Color.Unspecified,
                        contentDescription = "Map"
                    )
                }
                IconButton(onClick = {
                    val intent = Intent(context, LanguageSwitchActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                }) {
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

