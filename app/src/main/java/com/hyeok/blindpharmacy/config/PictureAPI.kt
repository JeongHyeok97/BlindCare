package com.hyeok.blindpharmacy.config

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PictureAPI{
    companion object{
        const val BASE_URL = "https://port-0-dnltod-17xco2nlstw0zyj.sel5.cloudtype.app"
    }


    @Multipart
    @POST("/upload/")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

}