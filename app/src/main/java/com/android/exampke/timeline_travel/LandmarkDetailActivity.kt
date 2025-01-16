package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class LandmarkDetailActivity : ComponentActivity() {
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
                    LandmarkDetailScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LandmarkDetailScreen(modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.icon_favorite),
                contentDescription = "favorite",
                tint = Color.Unspecified
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(color = Color.Blue)
                .width(210.dp)
                .height(280.dp)
        ) { Text("랜드마크의 대표 이미지") }
        Text("랜드마크 이름")
        Text("랜드마크 간단 주소지")
        Text("사진 보기  |  관련 영상  |  기본 정보")

        Text("타이틀 - 상세 정보")
        Text("상세 정보 내용, fold 가능")
        Text("타이틀 - 최근 뉴스")
        Text("관련 내용들, fold 가능")
        Text("필요시 YouTube API")
        YouTubePlayerScreen(videoId = "nF1zZIETE5k",)
        Text("타이틀 - 기본 정보")
        Text("기본 정보 내용들, fold 가능")
    }
}

@Composable
fun YouTubePlayerScreen(videoId: String) {
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        // 동영상 로드 (자동 재생되지 않음)
                        youTubePlayer.cueVideo(videoId, 0f) // 0f: 시작 시간
                    }
                })
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}