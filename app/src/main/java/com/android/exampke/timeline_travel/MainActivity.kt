package com.android.exampke.timeline_travel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Bitmap
import coil3.compose.AsyncImage
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme

class MainActivity : ComponentActivity() {
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
                    MainScreen(
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
fun MainScreen(
    modifier: Modifier
) {
    val context = LocalContext.current
    var capturedBitmap: Bitmap? by remember { mutableStateOf(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 카메라 앱에서 받은 Bitmap을 처리
            val bitmap = result.data?.extras?.getParcelable<Bitmap>("data")
            if (bitmap != null) {
                capturedBitmap = bitmap // Bitmap을 상태에 저장

                // Intent로 Bitmap 전달
                val intent = Intent(context, LoadCameraImageActivity::class.java)
                intent.putExtra("capturedImageBitmap", capturedBitmap) // Bitmap을 Intent에 전달
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "사진을 저장하지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 카메라
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 권한이 허락되면 카메라 실행
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        } else {
            // 권한이 거부되면 Toast로 메시지 표시
            Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val intent = Intent(context, LoadAlbumImageActivity::class.java)
                intent.putExtra("selectedImageUri", uri.toString())
                context.startActivity(intent)
            }
        }

    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp)
        ) {
            Button(
                onClick = {
                    requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier
                    .height(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_camera),
                    contentDescription = "camera",
                    tint = colorResource(R.color.theme_main_blue)
                )
                Text(
                    stringResource(R.string.open_camera),
                    color = colorResource(R.color.theme_main_blue)
                )
            }
            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = Modifier.height(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_album),
                    contentDescription = "album",
                    tint = colorResource(R.color.theme_main_blue)
                )
                Text(
                    stringResource(R.string.open_album),
                    color = colorResource(R.color.theme_main_blue)
                )
            }
        }
        SectionTitle(R.string.trending_landmark)
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
        ) {
            val randomLandmarks = getLandmarks().shuffled().take(5)
            randomLandmarks.forEach { landmark ->
                TrendLandmark(
                    landmark = landmark
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        SectionTitle(R.string.nearby_landmark)
        Row(
            modifier = Modifier
                .horizontalScroll(
                    rememberScrollState()
                )
                .weight(1f)
                .padding(end = 15.dp)
        ) {
            RegionalLandmark("수도권", R.drawable.korea_sudokwon, listOf("서울", "인천", "경기"))
            RegionalLandmark("충청도", R.drawable.korea_chungchung, listOf("충청", "대전"))
            RegionalLandmark("강원도", R.drawable.korea_gangwon, listOf("강원"))
            RegionalLandmark("경상도", R.drawable.korea_gyungsang, listOf("경상", "부산", "대구", "울산"))
            RegionalLandmark("전라도", R.drawable.korea_jullla, listOf("전라", "광주", "제주"))
        }
    }
}

@Composable
fun SectionTitle(topic: Int) {
    Text(
        stringResource(topic),
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        lineHeight = 50.sp,
        modifier = Modifier.padding(start = 15.dp)
    )
}

@Composable
fun TrendLandmark(landmark: Landmark) {
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
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    val intent = Intent(context, LandmarkDetailActivity::class.java).apply {
                        putExtra("landmark", landmark) // Landmark 객체 전달
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

                    context.startActivity(intent)
                }
                .border(
                    0.8.dp,
                    colorResource(R.color.theme_sub_blue),
                    RoundedCornerShape(20.dp)
                ),
        )
        Text(
            landmark.name,
            lineHeight = 30.sp,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp)
        )
        Text(landmark.location, lineHeight = 20.sp, modifier = Modifier.padding(start = 5.dp))
    }
}

@Composable
fun RegionalLandmark(region: String, map: Int, regionFilters: List<String>) {
    val context = LocalContext.current
    val filteredLandmarks = getLandmarks().filter { landmark ->
        regionFilters.any { filter -> landmark.location.contains(filter) }
    }
    Spacer(modifier = Modifier.width(15.dp))
    Box(
        modifier = Modifier
            .clickable {
                val intent = Intent(context, RegionalLandmarkActivity::class.java).apply {
                    putParcelableArrayListExtra("filteredLandmarks", ArrayList(filteredLandmarks))
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            }
            .height(256.dp)
            .width(144.dp)
            .border(
                0.8.dp,
                colorResource(R.color.theme_sub_orange),
                RoundedCornerShape(20.dp)
            )
            .background(Color.White)
            .padding(5.dp)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Image(
            painter = painterResource(id = map),
            contentDescription = "seoul",
            modifier = Modifier.align(alignment = Alignment.Center)
        )
        Text(
            region,
            lineHeight = 30.sp,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 10.dp)
        )
    }
}