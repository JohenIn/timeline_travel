package com.android.exampke.timeline_travel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.Bitmap


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.theme_main_blue),
        ), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.koinpotextlogo_main),
                    contentDescription = "koinpo",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .scale(0.3f)
                )
            }
        }
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar() {
    TopAppBar(modifier = Modifier
        .drawBehind {
        // 하단 Border 그리기
        drawLine(
            color = Color(0xFF6494FF),
            start = Offset(0f, size.height), // 시작점 (좌측 하단)
            end = Offset(size.width, size.height), // 끝점 (우측 하단)
            strokeWidth = 1.dp.toPx() // Border 두께
        )
    },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
        ), title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.koinpotextlogo),
                    contentDescription = "koinpo",
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .scale(0.3f)
                )
            }
        }
    )
}

@Composable
fun BottomNavigationBar() {
    val context = LocalContext.current
    val currentActivityName = (context as? Activity)?.javaClass?.simpleName ?: ""

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White)
            .graphicsLayer(clip = false)
            .drawBehind {
                drawLine(
                    color = Color(0x552b2b2b),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        BottomNaviButton(
            icon = R.drawable.icon_home,
            buttonName = R.string.home,
            context,
            currentActivityName,
            currentActivity = "MainActivity",
            destination = MainActivity::class.java
        )
        BottomFavButton(
            icon = R.drawable.icon_favorite,
            buttonName = R.string.favorite,
            context,
            currentActivityName,
            currentActivity = "FavoriteActivity",
            destination = FavoriteActivity::class.java
        )
        BottomCameraButton()
        BottomNaviButton(
            icon = R.drawable.icon_map,
            buttonName = R.string.map,
            context,
            currentActivityName,
            currentActivity = "MapActivity",
            destination = MapActivity::class.java
        )
        BottomNaviButton(
            icon = R.drawable.icon_language,
            buttonName = R.string.language,
            context,
            currentActivityName,
            currentActivity = "LanguageSwitchActivity",
            destination = LanguageSwitchActivity::class.java
        )
    }
}

@Composable
private fun BottomCameraButton(
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
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibrate: () -> Unit = {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(50)
        }
    }
    IconButton(
        onClick = {
            vibrate()
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        },
        modifier = Modifier
            .scale(1.5f)
            .padding(bottom = 0.dp)
            .background(
                color = colorResource(R.color.theme_main_blue), // 배경색
                shape = CircleShape // 원형 배경
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.icon_camera),
            contentDescription = "Camera",
            tint = Color.White, // 아이콘 색상: 흰색
            modifier = Modifier.padding(bottom = 5.dp)
        )
    }
}

@Composable
private fun BottomNaviButton(
    icon: Int,
    buttonName: Int,
    context: Context,
    currentActivityName: String,
    currentActivity: String,
    destination: Class<out Activity>
) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibrate: () -> Unit = {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(50)
        }
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                vibrate()
                val intent = Intent(context, destination)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            }) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Home",
            modifier = Modifier.scale(0.7f),
            tint = if (currentActivityName == currentActivity) colorResource(R.color.theme_sub_blue) else Color.Gray
        )
        Text(
            stringResource(buttonName),
            color = if (currentActivityName == currentActivity) colorResource(R.color.theme_sub_blue) else Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.offset(y = (-4).dp)
        )
    }
}

@Composable
private fun BottomFavButton(
    icon: Int,
    buttonName: Int,
    context: Context,
    currentActivityName: String,
    currentActivity: String,
    destination: Class<out Activity>
) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibrate: () -> Unit = {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val vibrationEffect =
                VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(50)
        }
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                vibrate()
                val intent = Intent(context, destination)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                context.startActivity(intent)
            }) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Home",
            modifier = Modifier.scale(0.7f),
            tint = if (currentActivityName == currentActivity) Color.Unspecified else Color.Gray
        )
        Text(
            stringResource(buttonName),
            color = if (currentActivityName == currentActivity) colorResource(R.color.theme_sub_blue) else Color.Gray,
            fontSize = 13.sp,
            modifier = Modifier.offset(y = (-4).dp)
        )
    }
}