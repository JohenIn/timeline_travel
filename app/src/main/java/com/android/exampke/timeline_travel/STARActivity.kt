package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import java.lang.reflect.Modifier

class STARActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timeline_travelTheme {
                STARScreen()
            }
        }
    }
}


@Composable
fun STARScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    // DB에서 데이터 가져오기
    val saveList = db.saveDataDao().getAll().collectAsState(emptyList())

    // name만 가져오기
    var name by remember { mutableStateOf("") }

    Column {
        // 리스트 항목 반복문
        Column {
            saveList.value.forEachIndexed { index, item ->
                Text(
                    text = "${item.name}",
                    fontSize = 15.sp
                )
            }
        }
    }
}