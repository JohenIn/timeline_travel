package com.android.exampke.timeline_travel

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                        modifier = Modifier.padding(innerPadding).background(Color.White),
                        capturedBitmap = capturedBitmap, // 전달받은 Bitmap을 화면에 전달,
                        currentLanguage = loadLanguagePreference(this) ?: "ko"

                    )
                }
            }
        }
    }
}

@Composable
fun LoadCameraImageScreen(modifier: Modifier,capturedBitmap: Bitmap?, currentLanguage: String) {
    val context = LocalContext.current
    val landmarkName = remember { mutableStateOf("랜드마크 이름을 로드 중...") }
    val landmarkDescription = remember { mutableStateOf("랜드마크 설명을 로드 중...") }
    val questionText = remember { mutableStateOf("") }
    val questionAnswer = remember { mutableStateOf("질문에 대한 답변이 여기에 표시됩니다.") }
    val landmarks = getLandmarks()  // 이곳은 랜드마크 데이터를 불러오는 함수입니다

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
                uploadImageToServer(it, landmarkName, landmarkDescription)
            } ?: Text("이미지를 로드할 수 없습니다.")
        }
        Spacer(modifier = Modifier.height(10.dp))

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
            modifier = Modifier.fillMaxWidth().align(Alignment.Start).padding(vertical = 15.dp)
        )
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black)
        )
        Text(landmarkDescription.value)
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(modifier = Modifier
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

private fun askQuestionToServer(question: String, landmarkName: String, onAnswerReceived: (String) -> Unit) {
    try {
        // Retrofit 호출
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

private var isRequestInProgress = false // 요청 상태를 추적하는 변수


private fun uploadImageToServer(
    bitmap: coil3.Bitmap?,
    landmarkName: MutableState<String>,
    landmarkDescription: MutableState<String>
) {
    if (bitmap == null) {
        Log.e("Upload", "Bitmap is null")
        landmarkName.value = "이미지가 비어 있습니다."
        landmarkDescription.value = "이미지가 null입니다."
        return
    }
    // 요청 중인지 확인
    if (isRequestInProgress) {
        Log.w("Upload", "이미 요청 중입니다.")
        return
    }
    isRequestInProgress = true // 요청 시작
    try {
        // Bitmap을 파일로 저장
        val file = File.createTempFile("upload_image", ".jpg")
        val isSaved = bitmap.saveToFile(file)
        if (!isSaved) {
            Log.e("Upload", "파일 저장 실패")
            landmarkName.value = "파일 저장 실패"
            landmarkDescription.value = "이미지를 파일로 저장하지 못했습니다."
            isRequestInProgress = false // 요청 종료
            return
        }
        // 파일을 MultipartBody.Part로 변환
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        // Retrofit 호출
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
                isRequestInProgress = false // 요청 종료
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("Upload", "서버 호출 실패: ${t.message}")
                landmarkName.value = "서버 호출 실패"
                landmarkDescription.value = "네트워크 연결을 확인하세요."
                isRequestInProgress = false // 요청 종료
            }
        })
    } catch (e: Exception) {
        Log.e("Upload", "이미지 업로드 중 오류: ${e.message}")
        landmarkName.value = "이미지 처리 오류"
        landmarkDescription.value = "이미지를 업로드하는 중 문제가 발생했습니다."
        isRequestInProgress = false // 요청 종료
    }
}