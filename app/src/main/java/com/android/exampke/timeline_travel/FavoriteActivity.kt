package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timeline_travelTheme {
                FavoriteScreen()
            }
        }
    }
}

@Composable
fun FavoriteScreen(){
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }

    // DB에서 데이터 가져오기
    val saveList = db.saveDataDao().getAll().collectAsState(emptyList())

    // input과 result 상태 정의
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "즐겨찾기 목록",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                color = Color(0xFFF0A0A0),
            )
        }
        // 리스트 항목 반복문
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            saveList.value.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splashbackgroundimage),
                        contentDescription = "MangoBanana",
                        modifier = Modifier
                            .width(200.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column() {
                        Text("남산타워", fontSize = 15.sp) // 코드를 넣어야할듯
                        Text("남산타워 설명 ~", fontSize = 10.sp) // 코드를 넣어야할듯
                    }
                }
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp)
//                ) {
//                    Text(
//                        text = "입력 : ${item.input}",
//                        fontSize = 15.sp,
//                        modifier = Modifier.weight(1f)
//
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        text = "번역 : ${item.result}",
//                        fontSize = 15.sp,
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }
//            Image(
//                painter = painterResource(id = R.drawable.back),
//                contentDescription = "닫기",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(50.dp)
//                    .clip(CircleShape)
//                    .clickable {
//                        val activity = context as? Activity
//                        activity?.finish()
//                    }
//            )
//            Text(
//                text = "뒤로",
//                fontSize = 15.sp
//            )
        }
    }

}