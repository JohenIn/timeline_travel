package com.android.exampke.timeline_travel

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import java.util.Locale

class LanguageSwitchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timeline_travelTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    LanguageSwitchScreen(
                        modifier = Modifier.padding(innerPadding).background(Color.White),
                        onLanguageSelected = { languageCode ->
                            // 언어 변경 후 앱 재시작
                            setLocale(this, languageCode)
                            saveLanguagePreference(this, languageCode)
                            restartApp()
                        }
                    )
                }
            }
        }
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

@Composable
fun LanguageSwitchScreen(
    modifier: Modifier,
    onLanguageSelected: (String) -> Unit // 언어 선택 콜백
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().
        padding(horizontal = 50.dp)
    ) {
        Spacer(modifier = Modifier.height( 50.dp))

        SectionTitle(R.string.select_language)
        Spacer(modifier = Modifier.height( 50.dp))
        Row(horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()) {
            // 한국어 버튼
            Icon(
                painter = painterResource(id = R.drawable.flag_korea),
                contentDescription = "Korean",
                tint = Color.Unspecified,
                modifier = Modifier.clickable { onLanguageSelected("ko")}.size(60.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.flag_japan),
                contentDescription = "Japan",
                tint = Color.Unspecified,
                modifier = Modifier.clickable { onLanguageSelected("ja") }.size(60.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.flag_usa),
                contentDescription = "English",
                tint = Color.Unspecified,
                modifier = Modifier.clickable { onLanguageSelected("en") }.size(60.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.flag_vietnam),
                contentDescription = "Vietnam",
                tint = Color.Unspecified,
                modifier = Modifier.clickable { onLanguageSelected("vi") }.size(60.dp)
            )
        }
    }
}

fun setLocale(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

fun saveLanguagePreference(context: Context, languageCode: String) {
    val preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    preferences.edit().putString("AppLanguage", languageCode).apply()
}

fun loadLanguagePreference(context: Context): String? {
    val preferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
    return preferences.getString("AppLanguage", "ko") // 기본값은 한국어
}