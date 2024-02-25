package com.hyeok.blindpharmacy.ui.manage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.chat.timestampToTimeString
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground

@Composable
fun ManageRoute(onSpeech: (String)->Unit, onStop:()->Unit){
    val drugViewModel = hiltViewModel<DrugsViewModel>()
    ManageScreen(drugViewModel, onSpeech,onStop)
}

@Composable
fun ManageScreen(drugViewModel: DrugsViewModel, onSpeech: (String) -> Unit, onStop: () -> Unit) {

    val log by drugViewModel.currentPage.observeAsState()
    Box(modifier = Modifier
        .fillMaxSize()
        .clickable {
            if (log != null) {
                val selectedDrug = log!!.drug
                println("${(System.currentTimeMillis()- log!!.timeStamp)/1000/60} 분")
                val space = (System.currentTimeMillis()- log!!.timeStamp)/1000/60
                val spaceNotify = if (space<60){
                    "을 ${(System.currentTimeMillis()- log!!.timeStamp)/1000/60} 분 전에 복용하셨습니다."
                }
                else{
                    "을 ${(System.currentTimeMillis()- log!!.timeStamp)/1000/60/60} 시간 전에 복용하셨습니다."
                }
                val text = "${selectedDrug.itemName} $spaceNotify \n ${(selectedDrug.useMethodQesitm)?.replace("\\n", "")}"
                onSpeech(text)
            }
        }
        .background(color = DefaultBackground)
        .padding(5.dp)){
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 30.dp),
            text = "복용 기록",
            fontSize = 26.sp,
            color = Color.White)
        if (log != null){
            DrugSchedule(log = log)
        }
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(85.dp),) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    onStop()
                    drugViewModel.setPrevDrug()
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )){
                Image(painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back")
                Text(text = "이전")
        }
            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick =
                {

                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )){
                Image(painter = painterResource(id = R.drawable.ic_detection),
                    contentDescription = "manage_camera")
                Text(text = "복용 관리 추가")
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                onClick = {
                    onStop()
                    drugViewModel.setNextDrug()
                },
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray
                )){
                Image(painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "next")
                Text(text = "다음")
            }



        }

    }
}

@Composable
fun BoxScope.DrugSchedule(log: DrugsLog?){
    val drug = log?.drug
    Column(modifier = Modifier
        .wrapContentSize()
        .align(Alignment.Center),
        verticalArrangement = Arrangement.spacedBy(30.dp)){
        if (drug?.itemImage==null){
            Image(painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "pill_placeHolder",
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally))
        }
        else{
            Image(painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = "pill_placeHolㄴder",
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.CenterHorizontally))
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "${drug?.itemName}"
        ,fontSize = 24.sp,
            color = Color.White)
        Text(text = "마지막 복용 \n\n${timestampToTimeString(log?.timeStamp!!,
            "yyyy 년 MM 월 dd 일" +
                    "HH 시 mm 분"
        )
    }",
            fontSize = 20.sp,
            color = Color.White)
        Text(text = "복용 방법 \n\n${drug?.useMethodQesitm?.replace("\\n", "")}",
            fontSize = 20.sp,
            color = Color.White)
    }
}

@Composable
@Preview
fun ManageScreenPreview(){
//    ManageScreen()
}





