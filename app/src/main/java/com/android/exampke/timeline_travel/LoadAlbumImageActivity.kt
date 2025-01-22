package com.android.exampke.timeline_travel

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.android.exampke.timeline_travel.model.ApiResponse
import com.android.exampke.timeline_travel.model.RetrofitClient
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadAlbumImageActivity : ComponentActivity() {
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
                    LoadAlbumImageScreen(
                        modifier = Modifier.padding(innerPadding).background(Color.White),
                        currentLanguage = loadLanguagePreference(this) ?: "ko"
                    )
                }
            }
        }
    }
}

@Composable
fun LoadAlbumImageScreen(modifier: Modifier, currentLanguage: String) {
    val activity = LocalContext.current as LoadAlbumImageActivity
    val context = LocalContext.current as LoadAlbumImageActivity
    // Intent로 전달된 이미지 가져오기
    val imageUriString = context.intent.getStringExtra("selectedImageUri")
    val imageUri = imageUriString?.let { Uri.parse(it) }
    // 서버로부터 받은 랜드마크 정보 상태
    val landmarkName = remember { mutableStateOf("랜드마크 이름을 로드 중...") }
    val landmarkDescription = remember { mutableStateOf("랜드마크 설명을 로드 중...") }
    // 질문-답변 관련 상태
    val questionText = remember { mutableStateOf("") }
    val questionAnswer = remember { mutableStateOf("질문에 대한 답변이 여기에 표시됩니다.") }
    val landmarks = getLandmarks()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(100.dp)
        ) {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it), // URI를 통해 이미지를 로드
                    contentDescription = "album image",
                    modifier = Modifier.fillMaxSize()
                )
// 앨범에서 가져온 이미지를 서버로 업로드 (자동 1회)
                // => 아래 rememberUploadOnce 호출
                rememberUploadOnce(
                    context = activity,
                    uri = it,
                    landmarkName = landmarkName,
                    landmarkDescription = landmarkDescription
                )
            } ?: Text("이미지를 불러올 수 없습니다.")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(stringResource(R.string.landmark_i_found), fontSize = 16.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(10.dp))

        val found = landmarks.find { it.name == landmarkName.value }
        found?.let { landmark ->
            Image(
                painter = rememberImagePainter(landmark.images),
                contentDescription = "랜드마크 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(210.dp)
                    .height(280.dp)
            )
        } ?: Text(stringResource(R.string.cannot_find_landmark))
        Spacer(modifier = Modifier.height(10.dp))
        LandmarkTitle(landmarkName)
        found?.let { landmark ->
        LandmarkLocation(landmark)}
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.description),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().align(Alignment.Start).padding(vertical = 15.dp)
        )
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text("설명: ${landmarkDescription.value}")
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = questionText.value,
            onValueChange = { questionText.value = it },
            placeholder = { Text(stringResource(R.string.enter_question)) },
            modifier = Modifier.fillMaxWidth()
        )
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
            Text(stringResource(R.string.ask_question))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(questionAnswer.value)
        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * Composable이 재구성(Recomposition)될 때마다 매번 uploadImageToServerFromUri가
 * 호출되지 않도록 한 번만 실행하는 래퍼 함수
 */
@Composable
private fun rememberUploadOnce(
    context: Context,
    uri: Uri,
    landmarkName: MutableState<String>,
    landmarkDescription: MutableState<String>
) {
    // 이미 업로드했는지 여부
    val hasUploadedState = remember { mutableStateOf(false) }
    // uri가 바뀔 때마다 다시 업로드할 필요가 있다면,
    // uri를 key로 해도 되고, 원하는 로직에 따라 수정 가능.
    val currentUri by rememberUpdatedState(uri)
    if (!hasUploadedState.value) {
        // 아직 업로드 전이면 업로드 진행
        uploadImageToServerFromUri(
            context = context,
            uri = currentUri,
            landmarkName = landmarkName,
            landmarkDescription = landmarkDescription
        )
        hasUploadedState.value = true
    }
}
private var isRequestInProgress = false
/**
 * 실제 파일 복사(임시파일) + 서버 업로드 로직
 */
private fun uploadImageToServerFromUri(
    context: Context,
    uri: Uri,
    landmarkName: MutableState<String>,
    landmarkDescription: MutableState<String>
) {
    // 이미 요청 중이라면 무시
    if (isRequestInProgress) {
        Log.w("Upload", "이미 요청 중입니다.")
        return
    }
    isRequestInProgress = true
    try {
        // 임시 파일 생성
        val tempFile = File.createTempFile("upload_image", ".jpg", context.cacheDir)
        // Uri → File 복사
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } ?: run {
            // inputStream이 null인 경우
            Log.e("Upload", "InputStream이 null입니다. Uri: $uri")
            landmarkName.value = "이미지를 읽을 수 없습니다."
            landmarkDescription.value = "해당 Uri에서 InputStream을 열 수 없습니다."
            isRequestInProgress = false
            return
        }
        // 파일을 MultipartBody.Part로 변환
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), tempFile)
        val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        // Retrofit을 통해 서버에 업로드
        val call = RetrofitClient.instance.uploadImage(body)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val applicationContext = MyApplication.appContext
                    landmarkName.value = apiResponse?.landmark ?: applicationContext.getString(R.string.unknown_landmark)
                    landmarkDescription.value = apiResponse?.answer ?: applicationContext.getString(R.string.cannot_find_description)
                } else {
                    Log.e("Upload", "서버 응답 오류: ${response.errorBody()?.string()}")
                    landmarkName.value = "서버 응답 오류"
                    landmarkDescription.value = "서버와의 통신 중 문제가 발생했습니다."
                }
                isRequestInProgress = false
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Upload", "서버 호출 실패: ${t.message}")
                landmarkName.value = "서버 호출 실패"
                landmarkDescription.value = "네트워크 연결을 확인하세요."
                isRequestInProgress = false
            }
        })
    } catch (e: Exception) {
        Log.e("Upload", "이미지 업로드 중 오류: ${e.message}")
        landmarkName.value = "이미지 처리 오류"
        landmarkDescription.value = "이미지를 업로드하는 중 문제가 발생했습니다."
        isRequestInProgress = false
    }
}
/**
 * 질문-답변 요청 함수 (카메라 액티비티와 동일하게 사용할 수 있음)
 */
private fun askQuestionToServer(
    question: String,
    landmarkName: String,
    onAnswerReceived: (String) -> Unit
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
