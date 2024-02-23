package com.hyeok.blindpharmacy.ui.chat

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.theme.MessageBoxBot
import com.hyeok.blindpharmacy.ui.theme.MessageBoxMe
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun RowScope.BottomButtons(
    buttonImageId:Int,
    buttonTextId:Int,
    onClick: ()->Unit
){
    Button(
        modifier = Modifier
            .weight(0.5f)
            .fillMaxHeight(),
        onClick = {onClick()},
        shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Image(painter = painterResource(id = buttonImageId), contentDescription = "Image Chat")
            Text(text = stringResource(id = buttonTextId))
        }

    }
}

@Composable
fun LazyItemScope.MessageBox(message: Message){
    val author = if (message.author == 0){
        stringResource(id = R.string.chat_bot)
    }
    else{
        stringResource(id = R.string.me)
    }
    val textBoxColor = if (message.author == 0){
        MessageBoxBot
    }
    else{
        MessageBoxMe
    }


    Column(modifier=Modifier
        ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = author,
            color= Color.White)
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Text(
                modifier= Modifier
                    .widthIn(max=300.dp)
                    .background(
                        color = textBoxColor,
                        shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                    .padding(5.dp),
                text = message.content)
            Text(
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .offset(x = 5.dp, y = (-5).dp),
                text = timestampToTimeString(message.timestamp),
                color= Color.White)
        }
        Spacer(modifier = Modifier.height(10.dp))

    }

}

@SuppressLint("SimpleDateFormat")
fun timestampToTimeString(timestamp:Long):String{
    try {
        val sdf = SimpleDateFormat("HH:mm")
        val netDate = Date(timestamp)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }

}