package com.hyeok.blindpharmacy.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale


@Composable
fun ChatRoute(onMessage: (String) -> Unit, onStopMessage: ()->Unit) {
    val chatViewModel:ChatViewModel = hiltViewModel<ChatViewModel>()
    ChatScreen(chatViewModel, onMessage, onStopMessage)
}

@Composable
fun ChatScreen(chatViewModel: ChatViewModel,
               onMessage: (String) -> Unit,
               onStopMessage: ()->Unit) {
    val context = LocalContext.current
    val conversations = remember { chatViewModel.conversation }

    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)
    val recognizerScope = rememberCoroutineScope()
    var recording by remember{
        mutableStateOf(false)
    }
    if (recording){
        RecordingDialog()
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
                onStopMessage()
                recording = true
                recognizerScope.launch {
                    requestPermission(context)
                    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                    intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
                    recognizer.setRecognitionListener(object : RecognitionListener{
                        override fun onReadyForSpeech(params: Bundle?) {

                        }

                        override fun onBeginningOfSpeech() {
                        // recognizer 의 startListening 이 호출 되면 동시에 호출 됩니다.
                        }

                        override fun onRmsChanged(rmsdB: Float) {

                        }

                        override fun onBufferReceived(buffer: ByteArray?) {
                        // recognizer 에 buffer , 즉 , 무언가 음성이 Text 로써 인식이 될 때마다
                        // ByteArray 로 이곳 에서 수신 가능 합니다.
                        }

                        override fun onEndOfSpeech() {
                            // recognizer 의 stopListening 이 호출 되면 동시에 호출 됩니다.
                        }

                        override fun onError(error: Int) {
                            // 에러가 발생
                        }

                        override fun onResults(results: Bundle?) {
                            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                            var content = ""

                            for (i in matches!!.indices){
                                content += matches[i]
                            }
                        // recognizer 의 stopListening 이 호출 된 후 별도의 Error 가 발생 하지 않을 경우
                        // 이곳, onResults 에서 Bundle 에 String Array 로 인식된 음성이 순서 대로 들어 있게 됩니다.

                        }

                        override fun onPartialResults(partialResults: Bundle?) {

                        }

                        override fun onEvent(eventType: Int, params: Bundle?) {

                        }
                    }
                    /*ChatSpeechRecognitionListener(context,
                        chatViewModel,
                        onMessage,
                        onStop = {
                            recording=false
                        }
                        )*/)
                    recognizer.startListening(intent)
                    delay(3000)
                    recognizer.stopListening()

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
                onStopMessage()
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
fun RecordingDialog(){
    Dialog(onDismissRequest = {
        }) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(
                modifier= Modifier.align(Alignment.Center),
                text = "음성 인식 중..",
                color = Color.White)
        }

    }
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
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO), 0)
    }
}

