package com.android.exampke.timeline_travel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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
import coil3.compose.AsyncImage
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class FavoriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Timeline_travelTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    topBar = {
                        TopBar()
                    },
                    bottomBar = {
                        BottomNavigationBar()
                    },
                ) { innerPadding ->
                    FavoriteScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

@Composable
fun FavoriteScreen(modifier: Modifier) {
    // getLandmarks 호출은 remember 외부에서 수행
    val landmarksList = getLandmarks()

    // landmarksList를 mutableStateListOf로 변환하여 상태 관리
    val landmarks = remember { mutableStateListOf(*landmarksList.toTypedArray()) }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SectionTitle(R.string.favorite_landmark)
        }
        // 리스트 항목 반복문
        val favoriteLandmarks = landmarks.filter { it.isFavorited }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            favoriteLandmarks.forEach { landmark ->
                FavoriteLandmark(
                    landmark = landmark,
                    onFavoriteChanged = { updatedLandmark ->
                        val index = landmarks.indexOfFirst { it.name == updatedLandmark.name }
                        // 즐겨찾기 상태 변경 시 리스트 갱신
                        if (index != -1) {
                            landmarks[index] = updatedLandmark.copy(
                                isFavorited = !updatedLandmark.isFavorited
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FavoriteLandmark(landmark: Landmark, onFavoriteChanged: (Landmark) -> Unit) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 15.dp)
            .width(IntrinsicSize.Max)
    ) {
        AsyncImage(
            model = landmark.images,
            contentDescription = "Landmark Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(240.dp)
                .width(180.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    val intent = Intent(context, LandmarkDetailActivity::class.java).apply {
                        putExtra("landmark", landmark) // Landmark 객체 전달
                    }
                    context.startActivity(intent)
                },
        )
        Text(landmark.name, lineHeight = 30.sp, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(landmark.location, lineHeight = 14.sp, modifier = Modifier.padding(start = 5.dp))
        Icon(
            painter = painterResource(R.drawable.icon_favorite),
            tint = if (landmark.isFavorited) Color.Unspecified else Color.Gray, // 색상 변경
            contentDescription = "Favorite",
            modifier = Modifier
                .size(20.dp) // 아이콘 크기 설정
                .clickable {
                    // 즐겨찾기 상태 변경
                    onFavoriteChanged(landmark) // 변경된 상태 반영
                }
        )
        Spacer(modifier = Modifier.height(50.dp))
    }
}