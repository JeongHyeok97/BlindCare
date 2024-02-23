package com.hyeok.blindpharmacy.ui.detection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun DetectionRoute() {
    val detectionViewModel = hiltViewModel<DetectionViewModel>()
    DetectionScreen(detectionViewModel)
}

@Composable
fun DetectionScreen(detectionViewModel: DetectionViewModel) {
    val context = LocalContext.current
    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {

            }
        }
    Scaffold {
        Box(modifier = Modifier.padding(it)){
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    1111
                )
            } else {
                takePhotoFromCameraLauncher.launch()
            }}) {

            }
        }
    }


}
