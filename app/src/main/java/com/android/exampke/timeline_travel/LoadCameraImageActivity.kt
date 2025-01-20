package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class LoadCameraImageActivity : ComponentActivity() {
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
                    val capturedBitmap = intent.getParcelableExtra<Bitmap>("capturedImageBitmap")
                    LoadCameraImageScreen(
                        modifier = Modifier.padding(innerPadding).background(Color.White),
                        capturedBitmap = capturedBitmap // 전달받은 Bitmap을 화면에 전달
                    )
                }
            }
        }
    }
}

@Composable
fun LoadCameraImageScreen(modifier: Modifier,capturedBitmap: Bitmap?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.icon_favorite),
                contentDescription = "favorite"
            )
        }
        Text(text = "내가 찍은 사진은?")
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.Transparent)
                .width(150.dp)
                .height(200.dp)
        ) {
            capturedBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "찍은 사진",
                    modifier = Modifier.fillMaxSize()
                )
            } ?: Text("없음")
        }
        Text("랜드마크 이름")
        Text("랜드마크 간단 주소지")
        Text("사진 보기  |  관련 영상  |  기본 정보")
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.Blue)
                .width(300.dp)
                .height(400.dp)
        ) { Text("랜드마크의 대표 이미지") }
        Text("타이틀 - 상세 정보")
        Text("상세 정보 내용, fold 가능")
        Text("타이틀 - 최근 뉴스")
        Text("관련 내용들, fold 가능")
        Text("필요시 YouTube API")
        Text("타이틀 - 기본 정보")
        Text("기본 정보 내용들, fold 가능")
    }
}