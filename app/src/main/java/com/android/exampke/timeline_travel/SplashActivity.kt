package com.android.exampke.timeline_travel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            Timeline_travelTheme {
                SplashScreen()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        },2000)

    }
}

@Composable
fun SplashScreen() {
    Box(modifier = Modifier
        .paint(painterResource(R.drawable.splashbackgroundimage),
            contentScale = ContentScale.FillHeight)
        .fillMaxSize()
){
    }
}