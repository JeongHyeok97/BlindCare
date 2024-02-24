package com.hyeok.blindpharmacy.ui.detection

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.config.PictureAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class DetectionViewModel @Inject constructor(
    private val pictureAPI: PictureAPI
): ViewModel() {

    private val mImageDescription = MutableLiveData<String>()
    private val mImage = MutableLiveData<Bitmap>()

    val imageDescription: LiveData<String>
        get() {
            return mImageDescription
        }

    val image: LiveData<Bitmap>
        get() {
            return mImage
        }


    suspend fun post(context: Context, bitmap: Bitmap, onDescription: (String)->Unit){
        withContext(Dispatchers.Main){
            mImage.postValue(bitmap)
        }
        val file = getFileFromBitmap(context, bitmap)
        if (file != null){
            val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
            val call = pictureAPI.uploadFile(multipartBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val responseBody = response.body()?.string()
                    if (response.isSuccessful){
                        val description = ObjectMapper().readTree(responseBody).get("description")
                        mImageDescription.postValue("$description")
                        onDescription("$description")
                    }
                    else{
                        mImageDescription.postValue("Failed")
                    }

                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}

fun getFileFromBitmap(context: Context, bitmap: Bitmap): File? {
    val file = File(context.cacheDir, "test.png")
    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}