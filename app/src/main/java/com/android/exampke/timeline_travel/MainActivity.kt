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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Bitmap
import coil3.compose.AsyncImage
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.android.exampke.timeline_travel.viewmodel.MapViewModel
import com.android.exampke.timeline_travel.viewmodel.ShowGoogleMap

class MainActivity : ComponentActivity() {
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
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
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
            Button(onClick = {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }, modifier = Modifier.height(50.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_camera),
                    contentDescription = "camera"
                )
                Text(stringResource(R.string.open_camera))
            }
            Button(onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }, modifier = Modifier.height(50.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_album),
                    contentDescription = "album"
                )
                Text(stringResource(R.string.open_album))
            }
        }
        Text(
            stringResource(R.string.trending_landmark),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            modifier = Modifier.padding(start = 15.dp)
        )
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
        Text(
            stringResource(R.string.nearby_landmark),
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            modifier = Modifier.padding(start = 15.dp)
        )
        ShowGoogleMap(
            mapViewModel = MapViewModel(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Composable
private fun TrendLandmark(landmark: Landmark) {
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
