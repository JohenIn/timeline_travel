package com.android.exampke.timeline_travel

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
            .graphicsLayer(clip = false).drawBehind {
                drawLine(color = Color(0x552b2b2b),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx())
            }
    ) {
        val context = LocalContext.current

        IconButton(onClick = {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(R.drawable.icon_home),
                contentDescription = "Home",
                modifier = Modifier.scale(0.9f)
            )
        }
        IconButton(onClick = {
            val intent = Intent(context, FavoriteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }) {
            Icon(
                painter = painterResource(R.drawable.icon_favorite),
//                    tint = Color.Unspecified, // Keep original color
                contentDescription = "Favorite",
                modifier = Modifier.scale(0.9f)
            )
        }
        IconButton(
            onClick = {
                val intent = Intent(context, CameraActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            },
            modifier = Modifier
                .scale(1.5f)
                .padding(bottom = 0.dp)
                .background(
                    color = Color(0xFF4382C4), // 배경색
                    shape = CircleShape // 원형 배경
                )
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_camera),
                contentDescription = "Camera",
                tint = Color.White, // 아이콘 색상: 흰색
                modifier = Modifier.padding(bottom = 5.dp) // 아이콘 패딩 추가
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
                contentDescription = "Map",
                modifier = Modifier.scale(0.9f)
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
                contentDescription = "Language",
                modifier = Modifier.scale(0.9f)
            )
        }
    }}

