package com.android.exampke.timeline_travel

import android.Manifest
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                        HomeTopBar()
                    },
                    bottomBar = {
                        BottomNavigationBar()
                    },
                ) { innerPadding ->
                    MainScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White),
                        currentLanguage = loadLanguagePreference(this) ?: "ko"
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    modifier: Modifier,
    currentLanguage: String
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.theme_main_blue))
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            HomeCameraButton()
            HomeAlbumButton()
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

        Text(currentLanguage,fontSize = 20.sp,fontWeight = FontWeight.ExtraBold,lineHeight = 50.sp,modifier = Modifier.padding(start = 15.dp))
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
            RegionalLandmark(
                R.string.region0_name, R.drawable.korea_sudokwon, listOf("서울", "Seoul", "ソウル","인천", "Incheon", "仁川","경기", "Gyeonggi", "京畿",)
            )
            RegionalLandmark(
                R.string.region1_name, R.drawable.korea_chungchung, listOf("충청", "Chungcheong", "忠清","대전", "Daejeon", "大田",)
            )
            RegionalLandmark(
                R.string.region2_name, R.drawable.korea_gangwon, listOf("강원", "Gangwon", "江原",)
            )
            RegionalLandmark(
                R.string.region3_name, R.drawable.korea_gyungsang, listOf("경상", "Gyeongsang", "慶尚","부산", "Busan", "釜山","대구", "Daegu", "大邱","울산", "Ulsan", "蔚山",)
            )
            RegionalLandmark(
                R.string.region4_name, R.drawable.korea_jullla, listOf("전라", "Jeolla", "全羅","광주", "Gwangju", "光州","제주", "Jeju", "済州",)
            )
        }
    }
}

@Composable
private fun HomeAlbumButton() {
    val context = LocalContext.current

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val intent = Intent(context, LoadAlbumImageActivity::class.java)
                intent.putExtra("selectedImageUri", uri.toString())
                context.startActivity(intent)
            }
        }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }.padding(horizontal = 20.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.icon_album),
            contentDescription = "album",
            tint = Color.White,
            modifier = Modifier.scale(1.2f)
        )
        Text(
            stringResource(R.string.open_album),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HomeCameraButton() {
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

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }.padding(horizontal = 20.dp)) {
        Icon(
            painter = painterResource(id = R.drawable.icon_camera),
            contentDescription = "camera",
            tint = Color.White,
            modifier = Modifier.scale(1.2f)
        )
        Text(
            stringResource(R.string.open_camera),
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Composable
fun SectionTitle(topic: Int) {
    Text(
        stringResource(topic),
        fontSize = 20.sp,
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
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            landmark.name,
            lineHeight = 20.sp,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 5.dp)
        )
        Text(
            landmark.location,
            lineHeight = 20.sp,
            modifier = Modifier.padding(start = 5.dp),
            color = Color.Gray,
            fontSize = 15.sp
        )
        landmark.news?.let {
            Text(
                landmark.news,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 5.dp),
                color = colorResource(R.color.theme_main_orange),
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
fun RegionalLandmark(region: Int, map: Int, regionFilters: List<String>) {
    val context = LocalContext.current
    val filteredLandmarks = getLandmarks().filter { landmark ->
        regionFilters.any { filter -> landmark.location.contains(filter) }
    }
    Spacer(modifier = Modifier.width(15.dp))
    Box(
        modifier = Modifier
            .border(
                0.8.dp,
                colorResource(R.color.theme_sub_orange),
                RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                val intent = Intent(context, RegionalLandmarkActivity::class.java).apply {
                    putParcelableArrayListExtra("filteredLandmarks", ArrayList(filteredLandmarks))
                    putExtra("region", region)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            }
            .width(144.dp)
            .height(256.dp)
            .background(Color.White)
            .padding(5.dp)

    ) {
        Image(
            painter = painterResource(id = map),
            contentDescription = "seoul",
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(bottom = 25.dp)
        )
        Text(
            stringResource(region),
            lineHeight = 20.sp,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            color = Color.DarkGray,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 10.dp)
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
}