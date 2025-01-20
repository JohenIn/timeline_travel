package com.android.exampke.timeline_travel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.AsyncImage
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandmarkDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val landmark = intent.getParcelableExtra<Landmark>("landmark")

        setContent {
            Timeline_travelTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    landmark?.let {
                        LandmarkDetailScreen(
                            modifier = Modifier
                                .padding(innerPadding)
                                .background(Color.White),
                            landmark = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LandmarkDetailScreen(modifier: Modifier, landmark: Landmark) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context) // 데이터베이스 인스턴스
    val scope = rememberCoroutineScope()

    // 즐겨찾기 상태 관리
    var isFavorited: Boolean = false
    val saveList = db.saveDataDao().getAll().collectAsState(emptyList())

    saveList.value.forEachIndexed { index, item ->
        if (item.landmarkName == landmark.name) {
            isFavorited = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.icon_favorite),
                contentDescription = "favorite",
                tint = if (isFavorited) Color.Unspecified else Color.Gray,
                modifier = Modifier
                    .clickable {
                        scope.launch(Dispatchers.IO) {
                            db.saveDataDao().insertAll(SaveData(landmarkName = landmark.name))
                        }
                    }
            )
        }
        AsyncImage(
            model = landmark.images,
            contentDescription = "Landmark Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(280.dp)
                .width(210.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(landmark.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(landmark.location, fontSize = 18.sp, color = Color.Gray)
        Text("History", fontWeight = FontWeight.Bold)
        Text(landmark.history)
        Text("Recent News", fontWeight = FontWeight.Bold)
        Text(landmark.recentNews)
        landmark.youTubeVideoId?.let { videoId ->
            Text("YouTube Video", fontWeight = FontWeight.Bold)
            YouTubePlayerScreen(videoId = videoId)
        }
        Text("Address", fontWeight = FontWeight.Bold)
        Text(landmark.address)
        Text("Opening Hours", fontWeight = FontWeight.Bold)
        Text(landmark.openingHours)
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