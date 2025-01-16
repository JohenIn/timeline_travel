package com.android.exampke.timeline_travel

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.android.exampke.timeline_travel.viewmodel.MapViewModel
import com.android.exampke.timeline_travel.viewmodel.ShowGoogleMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 앱 시작 시 저장된 언어 설정 적용
        val savedLanguage = loadLanguagePreference(this) ?: "ko" // 기본값은 한국어
        setLocale(this, savedLanguage)

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
    // 카메라
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // 권한이 허락되면 카메라 실행
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            context.startActivity(intent)
        } else {
            // 권한이 거부되면 Toast로 메시지 표시
            Toast.makeText(context, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let {
                val intent = Intent(context, LoadImageActivity::class.java)
                intent.putExtra("capturedImageUri", it.toString())
                context.startActivity(intent)
            }
        }
    }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val intent = Intent(context, LoadImageActivity::class.java)
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
                when {
                    ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        context.startActivity(intent)
                    }

                    else -> {
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                }
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
            TrendLandmark()
            TrendLandmark()
            TrendLandmark()
            TrendLandmark()
            TrendLandmark()
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
private fun TrendLandmark() {
    Column(
        modifier = Modifier
            .padding(start = 15.dp)
            .width(intrinsicSize = IntrinsicSize.Max)
    ) {
        val context = LocalContext.current
        Image(
            painter = painterResource(id = R.drawable.splashbackgroundimage),
            contentDescription = "oui",
            modifier = Modifier
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    val intent = Intent(context, LandmarkDetailActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    context.startActivity(intent)
                },
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            Text("경복궁", lineHeight = 30.sp, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Text("서울시 종로구", lineHeight = 14.sp, modifier = Modifier.padding(start = 5.dp))
    }
}