package com.android.exampke.timeline_travel.model

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/upload_with_language")
    fun uploadImageWithLanguage(
        @Part image: MultipartBody.Part,
        @Part("language") language: RequestBody // 언어 추가
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("/ask_with_language")
    fun askQuestionWithLanguage(
        @Field("landmark") landmark: String,
        @Field("question") question: String,
        @Field("language") language: String // 언어 추가
    ): Call<ApiResponse>

}