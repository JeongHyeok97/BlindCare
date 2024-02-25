package com.hyeok.blindpharmacy.ui.chat

import android.content.Context
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.widget.Toast

class ChatSpeechRecognitionListener(
    private val context: Context,
    private val chatViewModel: ChatViewModel,
    private val onMessage: (String)->Unit,
    private val onStop: ()->Unit): RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {

    }

    override fun onBeginningOfSpeech() {

    }

    override fun onRmsChanged(rmsdB: Float) {

    }

    override fun onBufferReceived(buffer: ByteArray?) {

    }

    override fun onEndOfSpeech() {
        onStop()
    }

    override fun onError(error: Int) {
//        Toast.makeText(context, "ERROR $error", Toast.LENGTH_SHORT).show()
        onStop()
    }

    override fun onResults(results: Bundle?) {
        onStop()
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var content = ""

        for (i in matches!!.indices){
            content += matches[i]
        }

        val message = Message(author = 1, content = content, timestamp = System.currentTimeMillis())
        chatViewModel.add(message)
        chatViewModel.searchDrugInfo(content, onMessage)

//        Toast.makeText(context, "INPUT ${message.content}", Toast.LENGTH_SHORT).show()

    }

    override fun onPartialResults(partialResults: Bundle?) {

    }

    override fun onEvent(eventType: Int, params: Bundle?) {

    }
}
