package com.hyeok.blindpharmacy.config

import com.hyeok.blindpharmacy.Drug
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DrugInfoApi {
    companion object{
        const val BASE_URL = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/"
        const val SERVICE_KEY = "kes4cXVdeMG9qc8SUiERNhs500qERoT1UUJPa4FjaJXHrKb9hx2x20X5MdbvaZe8kK8ru+yn7j/S8cDOA/H17Q=="
    }

    @GET("getDrbEasyDrugList")
    fun getData(
        @Query("serviceKey") serviceKey: String = SERVICE_KEY,
        @Query("itemName") itemName: String,
        @Query("type") type: String = "json"
    ): Call<ResponseBody>
}