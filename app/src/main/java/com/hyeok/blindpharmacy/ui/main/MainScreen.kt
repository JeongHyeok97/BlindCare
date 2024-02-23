@file:OptIn(ExperimentalMaterial3Api::class)

package com.hyeok.blindpharmacy.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground

@Composable
fun MainScreen(onClickMenu: (Menu)->Unit) {
    Scaffold(
        containerColor = DefaultBackground,
        topBar = { TopBar() }
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(10.dp)){
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(modifier = Modifier.weight(0.45f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MainButton(Menu.ChatBot, onClickMenu)
                    MainButton(Menu.Detection, onClickMenu)
                }
                Row(modifier = Modifier.weight(0.45f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    MainButton(Menu.DrugManage, onClickMenu)
                    MainButton(Menu.Setting, onClickMenu)
                }
            }
        }

    }
}

@Composable
fun RowScope.MainButton(menu:Menu, onClickMenu: (Menu)->Unit){
    Button(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        onClick = {onClickMenu(menu)},
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .offset(y = (-12).dp),
                painter = painterResource(id = menu.iconDrawableId), contentDescription = menu.text
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 36.dp),
                text = menu.text)
        }
    }
}


@Composable
fun TopBar(/*navController: NavHostController*/){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF242424)
        ),
        title = { Text(
            text = "의약품 관리",
            color = Color.White) },
    )
}

//@Preview(showBackground = true, device = "spec:width=411dp,height=891dp, orientation=portrait")
//@Composable
//fun MainPreview() {
//    Surface(color = DefaultBackground) {
//        MainScreen(){}
//    }
//}