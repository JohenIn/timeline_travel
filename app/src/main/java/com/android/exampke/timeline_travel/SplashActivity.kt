package com.android.exampke.timeline_travel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 앱 시작 시 저장된 언어 설정 적용
        val savedLanguage = loadLanguagePreference(this) ?: "ko" // 기본값은 한국어
        setLocale(this, savedLanguage)

        setContent {
            Timeline_travelTheme {
                SplashScreen()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)

    }
}

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .paint(
                painterResource(R.drawable.splashbackgroundimage),
                contentScale = ContentScale.FillHeight
            )
            .fillMaxSize()
            .padding(vertical = 100.dp, horizontal = 30.dp)
    ) {
        Text(
            stringResource(R.string.app_name),
            color = Color.Black,
            fontSize = 80.sp,
            lineHeight = 100.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(stringResource(R.string.splashscreen_subtitle), color = Color.White, fontSize = 40.sp)
    }
}