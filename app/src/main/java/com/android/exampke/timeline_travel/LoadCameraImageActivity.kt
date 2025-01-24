package com.android.exampke.timeline_travel

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil3.Bitmap
import com.android.exampke.timeline_travel.model.RetrofitClient
import com.android.exampke.timeline_travel.ui.theme.Timeline_travelTheme
import java.io.File
import com.android.exampke.timeline_travel.model.ApiResponse
import com.android.exampke.timeline_travel.utils.saveToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream

class LoadCameraImageActivity : ComponentActivity() {
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
                    val capturedBitmap = intent.getParcelableExtra<Bitmap>("capturedImageBitmap")
                    LoadCameraImageScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(Color.White),
                        capturedBitmap = capturedBitmap, // 전달받은 Bitmap을 화면에 전달,
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
    val context = LocalContext.current
    val landmarkName = remember { mutableStateOf("랜드마크 이름을 로드 중...") }
    val landmarkDescription = remember { mutableStateOf("랜드마크 설명을 로드 중...") }
    val questionText = remember { mutableStateOf("") }
    val questionAnswer = remember { mutableStateOf("질문에 대한 답변이 여기에 표시됩니다.") }
    val landmarks = getLandmarks()  // 이곳은 랜드마크 데이터를 불러오는 함수입니다
    var showDetails by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        landmarkName.value = context.getString(R.string.loadfromserver)
        landmarkDescription.value = context.getString(R.string.loadfromserver)
        questionAnswer.value = context.getString(R.string.questionAnswer)
    }

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

        // 랜드마크 제목, 이미지 등 표시
        Text(stringResource(R.string.landmark_i_found), fontSize = 16.sp, color = Color.DarkGray)
        Spacer(modifier = Modifier.height(10.dp))


        val found = landmarks.find { it.name == landmarkName.value }
        found?.let { landmark ->
            // 찾은 랜드마크의 이미지 URL을 사용하여 이미지 로드
            Image(
                painter = rememberAsyncImagePainter(landmark.images),
                contentDescription = "랜드마크 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(210.dp)
                    .height(280.dp)
            )
        } ?: Text(stringResource(R.string.cannot_find_landmark))
        Spacer(modifier = Modifier.height(10.dp))
        LandmarkTitle(landmarkName)

        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = stringResource(R.string.description),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(vertical = 15.dp)
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
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black)
        )
        Spacer(modifier = Modifier.height(20.dp))
        // 질문 입력창과 버튼
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
            Text(stringResource(R.string.ask_question))
        }
        Spacer(modifier = Modifier.height(20.dp))
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
            capturedBitmap.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, outputStream)
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
