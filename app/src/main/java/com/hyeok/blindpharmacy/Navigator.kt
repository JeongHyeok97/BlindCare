package com.hyeok.blindpharmacy

import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hyeok.blindpharmacy.Destination.CHAT_ROUTE
import com.hyeok.blindpharmacy.Destination.DETECTION_ROUTE
import com.hyeok.blindpharmacy.Destination.DRUG_MANAGE_ROUTE
import com.hyeok.blindpharmacy.Destination.SETTINGS_ROUTE
import com.hyeok.blindpharmacy.Destination.START_ROUTE
import com.hyeok.blindpharmacy.ui.chat.ChatRoute
import com.hyeok.blindpharmacy.ui.detection.DetectionRoute
import com.hyeok.blindpharmacy.ui.main.MainScreen
import com.hyeok.blindpharmacy.ui.main.Menu

object Destination{
    const val START_ROUTE = "main"
    const val CHAT_ROUTE = "chat"
    const val DETECTION_ROUTE = "detect"
    const val DRUG_MANAGE_ROUTE = "manager"
    const val SETTINGS_ROUTE = "setting"
}

@Composable
fun PharmacyNavHost(
    tts: TextToSpeech,
    navController: NavHostController = rememberNavController()) {


    NavHost(navController=navController, startDestination = START_ROUTE){

        composable(START_ROUTE){
            MainScreen { menu->
                when(menu){
                    Menu.ChatBot ->{
                        navController.navigate(CHAT_ROUTE)
                    }
                    Menu.Detection ->{
                        navController.navigate(DETECTION_ROUTE)
                    }
                    Menu.DrugManage ->{
                        navController.navigate(DRUG_MANAGE_ROUTE)
                    }
                    Menu.Setting ->{
                        navController.navigate(SETTINGS_ROUTE)
                    }
                }
            }
        }
        composable(CHAT_ROUTE){
            ChatRoute(){answer->
                tts.speak(answer, TextToSpeech.QUEUE_FLUSH, null, "answer")
            }
        }
        composable(DETECTION_ROUTE){
            DetectionRoute()
        }

        composable(DRUG_MANAGE_ROUTE){

        }
        composable(SETTINGS_ROUTE){

        }
    }
}





