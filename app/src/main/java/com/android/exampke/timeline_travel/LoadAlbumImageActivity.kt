package com.android.exampke.timeline_travel

import android.content.Context
import android.net.Uri
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.compose.rememberAsyncImagePainter
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
    val imageUriString = context.intent.getStringExtra("selectedImageUri")
    val imageUri = imageUriString?.let { Uri.parse(it) }

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
            modifier = Modifier
                .height(100.dp)
        ) {
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "album image",
                    modifier = Modifier.fillMaxSize()
                )
                uploadImageToServer(
                    context = activity,
                    uri = it,
                    landmarkName = landmarkName,
                    landmarkDescription = landmarkDescription,
                    currentLanguage = currentLanguage

                )
            } ?: Text("이미지를 불러올 수 없습니다.")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "내가 찾은 랜드마크는?", fontSize = 16.sp, color = Color.DarkGray)
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
        } ?: Text("랜드마크를 찾을 수 없습니다.")
        Text(currentLanguage, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 50.sp, modifier = Modifier.padding(start = 15.dp))
        Spacer(modifier = Modifier.height(10.dp))
        LandmarkTitle(landmarkName)
        found?.let { landmark ->
            LandmarkLocation(landmark)
        }
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "설명",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = if (showDetails) " ${landmarkDescription.value}" else "${landmarkDescription.value.take(100)}...",
                    color = Color.Black,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    modifier = Modifier.clickable { showDetails = !showDetails }
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
        TextField(
            value = questionText.value,
            onValueChange = { questionText.value = it },
            placeholder = { Text("질문을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                if (questionText.value.isNotBlank()) {
                    sendQuestionToServer(questionText.value, landmarkName.value,currentLanguage) { answer ->
                        questionAnswer.value = answer
                    }
                }
            }, colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier.align(Alignment.CenterHorizontally).background(colorResource(R.color.theme_sub_blue))
        ) {
            Text("질문 보내기")
        }
        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
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

// LoadAlbumImageActivity에서 사용하는 uploadImageToServer
private fun uploadImageToServer(
    context: Context,
    uri: Uri,
    landmarkName: MutableState<String>,
    landmarkDescription: MutableState<String>,
    currentLanguage: String // 언어 정보 추가
) {
    try {
        val tempFile = File.createTempFile("upload_image", ".jpg", context.cacheDir)
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), tempFile)
        val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        val languagePart = RequestBody.create("text/plain".toMediaTypeOrNull(), currentLanguage)

        val call = RetrofitClient.instance.uploadImageWithLanguage(body, languagePart)
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
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


private fun sendQuestionToServer(
    question: String,
    landmarkName: String,
    currentLanguage: String, // 언어 정보를 추가
    onAnswerReceived: (String) -> Unit
) {
    try {
        val call = RetrofitClient.instance.askQuestionWithLanguage(landmarkName, question, currentLanguage) // API 수정
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
