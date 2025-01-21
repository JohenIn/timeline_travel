package com.android.exampke.timeline_travel

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.viewinterop.AndroidView
import coil3.compose.AsyncImage
import com.android.exampke.timeline_travel.model.ApiResponse
import com.android.exampke.timeline_travel.model.RetrofitClient
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    val saveList = db.saveDataDao().getAll().collectAsState(emptyList())
    var isFavorited by remember { mutableStateOf(false) }

    val landmarkNameState = remember { mutableStateOf("서버에서 로딩중...") }
    val landmarkDescriptionState = remember { mutableStateOf("서버 설명 로딩중...") }
    // 서버 업로드 중복 요청 방지
    var isRequestInProgress by remember { mutableStateOf(false) }
    // 질문/답변 상태
    val questionText = remember { mutableStateOf("") }
    val questionAnswer = remember { mutableStateOf("질문에 대한 답변이 여기에 표시됩니다.") }
    val landmarkName = remember { mutableStateOf("랜드마크 이름을 로드 중...") }

    LaunchedEffect(saveList.value) {
        saveList.value.forEachIndexed { index, item ->
            if (item.landmarkName == landmark.name) {
                isFavorited = true
            }
        }
    }

    LaunchedEffect(landmark.images) {
        if (!isRequestInProgress) {
            isRequestInProgress = true
            val downloadedFile = downloadImageToFile(context, landmark.images)
            if (downloadedFile != null) {
                uploadImageToServer(
                    file = downloadedFile,
                    onSuccess = { serverLandmark, serverAnswer ->
                        landmarkNameState.value = serverLandmark
                        landmarkDescriptionState.value = serverAnswer
                        isRequestInProgress = false
                    },
                    onError = { errorMsg ->
                        landmarkNameState.value = "오류 발생"
                        landmarkDescriptionState.value = errorMsg
                        isRequestInProgress = false
                    }
                )
            } else {
                landmarkNameState.value = "이미지 다운로드 실패"
                landmarkDescriptionState.value = "이미지 URL이 잘못되었거나 네트워크 문제"
                isRequestInProgress = false
            }
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
                            if (isFavorited) {
                                db.saveDataDao().deleteByName(landmark.name)
                            } else {
                                db.saveDataDao().insertAll(SaveData(landmarkName = landmark.name))
                            }
                            isFavorited = !isFavorited
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
        Spacer(modifier = Modifier.height(10.dp))
        // (서버 응답) 랜드마크 이름, 설명
        Text("${landmarkNameState.value}", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = stringResource(R.string.description),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().align(Alignment.Start).padding(horizontal = 15.dp)
            )
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black)
        )
        Text("${landmarkDescriptionState.value}")
        Spacer(modifier = Modifier.height(20.dp))

        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(20.dp))

        landmark.youTubeVideoId?.let { videoId ->
            Text("YouTube Video", fontWeight = FontWeight.Bold)
            YouTubePlayerScreen(videoId = videoId)
        }
        Spacer(modifier = Modifier.height(20.dp))
        // 질문 입력 + 버튼
        TextField(
            value = questionText.value,
            onValueChange = { questionText.value = it },
            placeholder = { Text("질문을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (questionText.value.isNotBlank()) {
                    askQuestionToServer(questionText.value, landmarkName.value) { answer ->
                        questionAnswer.value = answer
                    }
                }
            },colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier.align(Alignment.CenterHorizontally).background(colorResource(R.color.theme_sub_blue))
        ) {
            Text("질문 보내기")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text("${questionAnswer.value}")
        Spacer(modifier = Modifier.height(20.dp))
        // 이하 기존 상세 정보 (DB/서버에서 이미 받은 데이터)
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

suspend fun downloadImageToFile(context: Context, imageUrl: String): File? {
    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val request = Request.Builder().url(imageUrl).build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("Download", "이미지 다운로드 실패: ${response.message}")
                return@withContext null
            }
            val body: ResponseBody? = response.body
            if (body == null) {
                Log.e("Download", "ResponseBody가 null입니다.")
                return@withContext null
            }
            val tempFile = File.createTempFile("landmark_download", ".jpg", context.cacheDir)
            body.byteStream().use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("Download", "이미지 다운로드 중 예외 발생: ${e.message}")
            null
        }
    }
}

/**
 * (2) 파일(이미지) → 서버 업로드 & 응답 받기
 */
fun uploadImageToServer(
    file: File,
    onSuccess: (String, String) -> Unit,
    onError: (String) -> Unit
) {
    try {
        // Multipart 변환
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        // Retrofit 호출
        val call = RetrofitClient.instance.uploadImage(body)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val landmarkName = apiResponse?.landmark ?: "알 수 없는 랜드마크"
                    val description = apiResponse?.answer ?: "설명을 가져올 수 없습니다."
                    onSuccess(landmarkName, description)
                } else {
                    Log.e("Upload", "서버 응답 오류: ${response.errorBody()?.string()}")
                    onError("서버 응답 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Upload", "서버 호출 실패: ${t.message}")
                onError("서버 호출 실패 / 네트워크 오류")
            }
        })
    } catch (e: Exception) {
        Log.e("Upload", "이미지 업로드 중 예외: ${e.message}")
        onError("이미지 업로드 중 예외 발생")
    }
}
/**
 * (3) 질문-답변 API
 */
private fun askQuestionToServer(question: String,landmarkName: String,onAnswerReceived: (String) -> Unit
) {
    try {
        val call = RetrofitClient.instance.askQuestion(landmarkName, question)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    onAnswerReceived(apiResponse?.answer ?: "답변을 가져올 수 없습니다.")
                } else {
                    Log.e("AskQuestion", "서버 응답 오류: ${response.errorBody()?.string()}")
                    onAnswerReceived("서버 응답 오류가 발생했습니다.")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("AskQuestion", "서버 호출 실패: ${t.message}")
                onAnswerReceived("네트워크 오류가 발생했습니다.")
            }
        })
    } catch (e: Exception) {
        Log.e("AskQuestion", "질문 전송 중 오류: ${e.message}")
        onAnswerReceived("질문 처리 중 오류가 발생했습니다.")
    }
}