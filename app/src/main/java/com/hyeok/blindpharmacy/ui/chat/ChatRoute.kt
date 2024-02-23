package com.hyeok.blindpharmacy.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground


@Composable
fun ChatRoute(onAnswer: (String) -> Unit) {
    val chatViewModel:ChatViewModel = viewModel<ChatViewModel>()
    ChatScreen(chatViewModel)
}

@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {
    val conversations = remember { chatViewModel.conversation }
    ChatBox(conversations = conversations)
}





@Composable
fun ChatBox(conversations: SnapshotStateList<Message>){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = DefaultBackground)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .align(Alignment.BottomCenter)
            .padding(horizontal = 5.dp)) {
            BottomButtons(buttonImageId = R.drawable.ic_mic, buttonTextId = R.string.chat_mic) {

            }
            Spacer(modifier = Modifier
                .width(5.dp)
            )
            BottomButtons(buttonImageId = R.drawable.ic_pill, buttonTextId = R.string.chat_image) {

            }
        }

        LazyColumn(
            reverseLayout = true,
            modifier= Modifier
                .fillMaxSize()
                .offset(y = (-85).dp)
                .padding(horizontal = 5.dp)
        ){
            items(conversations.toList()){
                message-> MessageBox(message = message)
            }
        }
    }
}








//@Composable
//@Preview
//fun ChatScreenPreview(){
//    Surface {
//        val conversations = remember { mutableStateListOf<Message>(
//            Message(0, "약품 검색을 도와드려요. " +
//                    "좌측 하단을 누른 뒤 음성으로 '약품명' 알려줘 와 같이 질문해주시거나 " +
//                    "우측 하단을 누른 뒤 약품이 있는 곳을 촬영해주세요!", System.currentTimeMillis()),
//            Message(1, "게보린 에 대해 알려줘", System.currentTimeMillis()),
//
//            Message(0, "- 두통, 치통, 발치(이를 뽑음)후 동통(통증), 인후(목구멍)통, 귀의 통증, 관절통, 신경통, 요(허리)통, 근육통, 견통(어깨통증), 타박통, 골절통, 염좌통(삔 통증), 월경통(생리통), 외상(상처)통의 진통" +
//                    "\n" +
//                    "- 오한(춥고 떨리는 증상), 발열시의 해열 등에 사용하실 수 있는 약이에요.", System.currentTimeMillis()),
//        ) }
//        ChatBox(conversations = conversations)
//    }
//}

