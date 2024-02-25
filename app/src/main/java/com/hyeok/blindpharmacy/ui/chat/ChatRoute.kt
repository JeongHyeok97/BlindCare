package com.hyeok.blindpharmacy.ui.chat

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
//    val activityResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        val intent = it.data
//        if (intent != null){
//
//        }
//
//    }


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
                    recognizer.setRecognitionListener(ChatSpeechRecognitionListener(context,
                        chatViewModel,
                        onMessage,
                        onStop = {
                            recording=false
                        }
                        ))
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
    // 버전 체크, 권한 허용했는지 체크
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.RECORD_AUDIO), 0)
    }
}

