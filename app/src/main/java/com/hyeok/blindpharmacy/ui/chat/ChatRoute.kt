package com.hyeok.blindpharmacy.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale



@Composable
fun ChatRoute(onMessage: (String) -> Unit) {
    val chatViewModel:ChatViewModel = hiltViewModel<ChatViewModel>()
    ChatScreen(chatViewModel, onMessage)
}

@Composable
fun ChatScreen(chatViewModel: ChatViewModel,
               onMessage: (String) -> Unit) {
    val context = LocalContext.current
    val conversations = remember { chatViewModel.conversation }
    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val recognizerScope = rememberCoroutineScope()
    val activityResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val intent = it.data
        if (intent != null){

        }

    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = DefaultBackground)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .align(Alignment.BottomCenter)
            .padding(horizontal = 5.dp)) {
            BottomButtons(buttonImageId = R.drawable.ic_mic, buttonTextId = R.string.chat_mic) {
                recognizerScope.launch {
                    requestPermission(context)
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
                    )
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "검색을 원하시는 약품명을 말해주세요")
                    recognizer.setRecognitionListener(object : RecognitionListener{
                        override fun onReadyForSpeech(params: Bundle?) {

                        }

                        override fun onBeginningOfSpeech() {

                        }

                        override fun onRmsChanged(rmsdB: Float) {

                        }

                        override fun onBufferReceived(buffer: ByteArray?) {
                            println("println $buffer")
                        }

                        override fun onEndOfSpeech() {

                        }

                        override fun onError(error: Int) {

                        }

                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            var content = ""

                            for (i in matches!!.indices){
                                content += matches[i]
                            }

                            val message = Message(author = 1, content = content, timestamp = System.currentTimeMillis())
                            chatViewModel.add(message)
                            chatViewModel.searchDrugInfo(content, onMessage)

                        }

                        override fun onPartialResults(partialResults: Bundle?) {

                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {

                        }
                    }
                    )
                    recognizer.startListening(intent)
                    delay(3000)

                    recognizer.stopListening()
                    recognizer.destroy()
//                    activityResult.launch(intent)
                }
                if (!SpeechRecognizer.isRecognitionAvailable(context)) {
                    Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
                } else {

                }
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
                    message->
                MessageBox(message = message)
                if (conversations.toList().size==1){
                    onMessage(message.content)
                }
            }
        }
    }
}





@Composable
fun ChatBox(chatViewModel: ChatViewModel){

}




private fun getSpeechInput(context: Context) {
    if (!SpeechRecognizer.isRecognitionAvailable(context)) {
        Toast.makeText(context, "Speech not Available", Toast.LENGTH_SHORT).show()
    } else {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
    }
}


private fun requestPermission(context: Context) {
    // 버전 체크, 권한 허용했는지 체크
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO), 0)
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

