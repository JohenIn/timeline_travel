package com.android.exampke.timeline_travel

import android.content.Context
import android.graphics.Bitmap // 안드로이드 표준 Bitmap (coil3.Bitmap이 아니라 이걸 써주세요)
import android.graphics.Bitmap.CompressFormat
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.android.exampke.timeline_travel.model.ApiResponse
import com.android.exampke.timeline_travel.model.RetrofitClient
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class LoadCameraImageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Timeline_travelTheme {
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = { BottomNavigationBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // 인텐트로부터 전달받은 Bitmap (카메라 촬영 결과)
                    val capturedBitmap = intent.getParcelableExtra<Bitmap>("capturedImageBitmap")

                    LoadCameraImageScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White),
                        capturedBitmap = capturedBitmap,
                        currentLanguage = loadLanguagePreference(this) ?: "ko"
                    )
                }
            }
        }
    }
}

@Composable
fun LoadCameraImageScreen(
    modifier: Modifier,
    capturedBitmap: Bitmap?,
    currentLanguage: String
) {
    // Composable에서 사용할 상태
    val context = LocalContext.current
    val landmarkName = remember { mutableStateOf("랜드마크 이름을 로드 중...") }
    val landmarkDescription = remember { mutableStateOf("랜드마크 설명을 로드 중...") }
    val questionText = remember { mutableStateOf("") }
    val questionAnswer = remember { mutableStateOf("질문에 대한 답변이 여기에 표시됩니다.") }
    val landmarks = getLandmarks()
    var showDetails by remember { mutableStateOf(false) }

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
            modifier = Modifier.height(100.dp)
        ) {
            // 1) 찍은 사진 보여주기
            capturedBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "찍은 사진",
                    modifier = Modifier.fillMaxSize()
                )
                // 2) 서버 업로드 (언어 + 이미지)
                uploadCameraImageToServer(
                    context = context,
                    capturedBitmap = it,
                    landmarkName = landmarkName,
                    landmarkDescription = landmarkDescription,
                    currentLanguage = currentLanguage
                )
            } ?: Text("이미지를 가져올 수 없습니다.")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 현재 언어 표시 (디버깅/확인용)
        Text(
            text = currentLanguage,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            lineHeight = 50.sp,
            modifier = Modifier.padding(start = 15.dp)
        )

        // 랜드마크 제목, 이미지 등 표시
        Text(text = "내가 찾은 랜드마크는?", fontSize = 16.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(10.dp))

        val found = landmarks.find { it.name == landmarkName.value }
        found?.let { landmark ->
            Image(
                painter = rememberAsyncImagePainter(landmark.images),
                contentDescription = "랜드마크 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(210.dp)
                    .height(280.dp)
            )
        } ?: Text("랜드마크를 찾을 수 없습니다.")

        Spacer(modifier = Modifier.height(10.dp))

        // 랜드마크 이름
        LandmarkTitle(landmarkName)
        Spacer(modifier = Modifier.height(5.dp))

        // 랜드마크 "설명"
        Text(
            text = "설명",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                val displayText = if (showDetails) {
                    landmarkDescription.value
                } else {
                    // 앞 100자만 보여주기
                    landmarkDescription.value.take(100) + "..."
                }

                Text(
                    text = displayText,
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    modifier = Modifier.clickable {
                        showDetails = !showDetails
                    }
                )

                if (!showDetails) {
                    Text(
                        text = "자세히 보기",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { showDetails = true }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 질문 입력 필드
        TextField(
            value = questionText.value,
            onValueChange = { questionText.value = it },
            placeholder = { Text("질문을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 질문 전송 버튼
        Button(
            onClick = {
                if (questionText.value.isNotBlank()) {
                    sendQuestionToServer(
                        question = questionText.value,
                        landmarkName = landmarkName.value,
                        currentLanguage = currentLanguage
                    ) { answer ->
                        questionAnswer.value = answer
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .background(colorResource(R.color.theme_sub_blue))
        ) {
            Text("질문 보내기")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 질문 답변 표시
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = questionAnswer.value,
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

/**
 * 1) 카메라로 찍은 Bitmap 이미지를 임시파일로 변환 후
 *    언어 + 이미지 = 서버로 업로드하는 함수
 */
private fun uploadCameraImageToServer(
    context: Context,
    capturedBitmap: Bitmap,
    landmarkName: MutableState<String>,
    landmarkDescription: MutableState<String>,
    currentLanguage: String
) {
    try {
        // Bitmap -> 임시 파일
        val tempFile = File.createTempFile("upload_image", ".jpg", context.cacheDir)
        FileOutputStream(tempFile).use { outputStream ->
            // 압축 형식 JPEG, 화질 100%
            capturedBitmap.compress(CompressFormat.JPEG, 100, outputStream)
        }

        // Retrofit을 위한 RequestBody
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), tempFile)
        val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)

        // 언어 파라미터
        val languagePart = RequestBody.create("text/plain".toMediaTypeOrNull(), currentLanguage)

        // uploadImageWithLanguage API 호출
        val call = RetrofitClient.instance.uploadImageWithLanguage(body, languagePart)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    // 결과로부터 랜드마크/설명 수령
                    landmarkName.value = apiResponse?.landmark ?: "알 수 없는 랜드마크"
                    landmarkDescription.value = apiResponse?.answer ?: "설명을 가져올 수 없습니다."
                } else {
                    Log.e("Upload", "서버 응답 오류: ${response.errorBody()?.string()}")
                    landmarkName.value = "서버 응답 오류"
                    landmarkDescription.value = "서버와의 통신 중 문제가 발생했습니다."
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Upload", "서버 호출 실패: ${t.message}")
                landmarkName.value = "서버 호출 실패"
                landmarkDescription.value = "네트워크 연결을 확인하세요."
            }
        })
    } catch (e: Exception) {
        Log.e("Upload", "이미지 업로드 중 오류: ${e.message}")
        landmarkName.value = "이미지 처리 오류"
        landmarkDescription.value = "이미지를 업로드하는 중 문제가 발생했습니다."
    }
}

/**
 * 2) 질문 전송: 언어/랜드마크/질문 -> 서버
 */
private fun sendQuestionToServer(
    question: String,
    landmarkName: String,
    currentLanguage: String,
    onAnswerReceived: (String) -> Unit
) {
    try {
        val call = RetrofitClient.instance.askQuestionWithLanguage(
            landmark = landmarkName,
            question = question,
            language = currentLanguage
        )
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
