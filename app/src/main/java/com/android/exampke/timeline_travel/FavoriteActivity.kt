package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class FavoriteActivity : ComponentActivity() {
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
                    FavoriteScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun FavoriteScreen(modifier: Modifier) {

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    // DB에서 데이터 가져오기
    val saveList = db.saveDataDao().getAll().collectAsState(emptyList())
    // input과 result 상태 정의
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                stringResource(R.string.favorite_landmark),
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
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//            saveList.value.forEachIndexed { index, item ->
            Row(
                modifier = Modifier
//                    .padding(start = 10.dp)
                    .fillMaxWidth()
                    .clickable{
                        // 화면 전환 로직
//                        navigateToDetailScreen()
                    },
                verticalAlignment = Alignment.Top
            ) {
                Image(
                    painter = painterResource(id = R.drawable.timeline),
                    contentDescription = "랜드마크 이미지",
                    modifier = Modifier
                        .width(100.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Text( // 랜드마크명
                        text = "랜드마크명", //${item.input}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text( // 랜드마크명
                        text = "랜드마크간단주소지", //${item.input}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text( // 랜드마크 설명
                        text = "랜드마크 설명(20자까지만 보여주고 그 이후는 ...", //${item.result}",
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier
                    .weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.icon_favorite),
                    tint = Color.Unspecified,// 별 아이콘 리소스
                    contentDescription = "Favorite",
                    modifier = Modifier
//                        .align(Alignment.TopEnd) // 오른쪽 위에 정확히 배치
                        .clickable {
                            // 별 아이콘 클릭 시 처리할 로직
                        }
//                        .padding(8.dp) // 아이콘과 화면 모서리 간의 간격
                        .size(20.dp) // 아이콘 크기 설정
                )
            }
        }
    }
}