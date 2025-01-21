package com.android.exampke.timeline_travel.model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/upload")
    fun uploadImage(
        @Part image: MultipartBody.Part
    ): Call<ApiResponse>

    @FormUrlEncoded
    @POST("/ask")
    fun askQuestion(
        @Field("landmark") landmark: String,
        @Field("question") question: String
    ): Call<ApiResponse>
}