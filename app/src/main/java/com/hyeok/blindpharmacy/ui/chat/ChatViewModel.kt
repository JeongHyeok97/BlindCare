package com.hyeok.blindpharmacy.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.hyeok.blindpharmacy.Drug
import com.hyeok.blindpharmacy.config.DrugInfoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/*@HiltViewModel*/
@HiltViewModel
class ChatViewModel @Inject constructor(
    private val drugInfoRepository: DrugInfoRepository
) : ViewModel(){
    val conversation : SnapshotStateList<Message> = mutableStateListOf()


    fun searchDrugInfo(itemName: String, onMessage: (String)->Unit){
        val data = drugInfoRepository.getDrugInfo(itemName)
        data.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful){
                    val drugInfoResponse = response.body()?.string()
                    val objectMapper = ObjectMapper()
                    val responseTree = objectMapper.readTree(drugInfoResponse)
                    val resultCode = responseTree.get("header").get("resultCode").asText()
                    if (resultCode == "00"){
                        val item = responseTree.get("body").get("items").get(0)
                        val drug = objectMapper.treeToValue(item, Drug::class.java)
                        val effect = drug.efcyQesitm
                        if (effect != null){
                            onMessage(effect)
                            conversation.add(
                                Message(author = 0, content = effect, timestamp = System.currentTimeMillis())
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    init {
       conversation.add(
           Message(0, "약품 검색을 도와드려요. " +
                   "좌측 하단을 누른 뒤 음성으로 '약품명' 알려줘 와 같이 질문해주시거나 " +
                   "우측 하단을 누른 뒤 약품이 있는 곳을 촬영해주세요!", System.currentTimeMillis())
       )

    }
    fun add(message: Message){
        conversation.add(message)
    }
}

class DrugInfoRepository(
    private val drugInfoApi: DrugInfoApi
) {
    fun getDrugInfo(itemName: String): Call<ResponseBody> {
        return drugInfoApi.getData(itemName = itemName)
    }
}