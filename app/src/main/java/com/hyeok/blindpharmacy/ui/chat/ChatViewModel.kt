package com.hyeok.blindpharmacy.ui.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyeok.blindpharmacy.config.LangChainAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/*@HiltViewModel*/
class ChatViewModel /*@Inject constructor(
    langChainAPI: LangChainAPI
)*/ : ViewModel(){
    val conversation : SnapshotStateList<Message> = mutableStateListOf()



    init {
       conversation.add(
           Message(0, "약품 검색을 도와드려요. " +
                   "좌측 하단을 누른 뒤 음성으로 '약품명' 알려줘 와 같이 질문해주시거나 " +
                   "우측 하단을 누른 뒤 약품이 있는 곳을 촬영해주세요!", System.currentTimeMillis())
       )
    }
    fun quest(message: Message){

    }
}