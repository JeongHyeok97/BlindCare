package com.hyeok.blindpharmacy.ui.chat

import android.graphics.Bitmap

data class Message(
    val author: Int,
    val content: String,
    val timestamp: Long,
    val image: Bitmap? = null
    )
