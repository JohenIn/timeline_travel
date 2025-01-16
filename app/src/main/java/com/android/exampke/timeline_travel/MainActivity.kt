package com.android.exampke.timeline_travel

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.android.exampke.timeline_travel.viewmodel.MapViewModel
import com.android.exampke.timeline_travel.viewmodel.ShowGoogleMap
import kotlin.contracts.contract

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

    // 앨범


    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 30.dp)
        ) { //카메라
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
            }, modifier = Modifier.height(200.dp))
            {
                Icon(
                    painter = painterResource(id = R.drawable.icon_camera),
                    contentDescription = "camera"
                )
                Text("카메라 오픈 버튼")
            } // 앨범
            Button(onClick = {

            }, modifier = Modifier.height(200.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_camera),
                    contentDescription = "camera"
                )
                Text("앨범 오픈 버튼")
            }
        }
        Text(
            "요즘 뜨는 장소",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            modifier = Modifier.padding(start = 15.dp)
        )
        Row(
            modifier = Modifier
                .padding(end = 8.dp)
                .horizontalScroll(rememberScrollState())
        ) {
//            TrendLandmark()
//            TrendLandmark()
//            TrendLandmark()
//            TrendLandmark()
//            TrendLandmark()
//            TrendLandmark()
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            "근처 랜드마크",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            modifier = Modifier.padding(start = 15.dp)
        )
        ShowGoogleMap(
            mapViewModel = MapViewModel(),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
        )
    }
}

@Composable
private fun TrendLandmark() {
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .width(intrinsicSize = IntrinsicSize.Max)
    ) {
        Image(
            painter = painterResource(id = R.drawable.splashbackgroundimage),
            contentDescription = "oui",
            modifier = Modifier
                .height(350.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {},
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