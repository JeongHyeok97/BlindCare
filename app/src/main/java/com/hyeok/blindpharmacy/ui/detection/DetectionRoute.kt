package com.hyeok.blindpharmacy.ui.detection

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hyeok.blindpharmacy.R
import com.hyeok.blindpharmacy.ui.theme.DefaultBackground
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun DetectionRoute(onDescription: (String)->Unit) {
    val detectionViewModel = hiltViewModel<DetectionViewModel>()
    DetectionScreen(detectionViewModel, onDescription)
}

@Composable
fun DetectionScreen(detectionViewModel: DetectionViewModel, onDescription: (String)->Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val image by detectionViewModel.image.observeAsState()
    val description by detectionViewModel.imageDescription.observeAsState()

    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                scope.launch(Dispatchers.IO) {
//                    val drawable = ContextCompat.getDrawable(context, R.drawable.hanabi)
//                    val bitmap = (drawable as BitmapDrawable).bitmap
//                    detectionViewModel.post(context, bitmap, onDescription)
                    detectionViewModel.post(context, takenPhoto, onDescription)
                }
            }
        }
    DetectionBox(resultImageState = image, resultDescriptionState = description) {
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
        }}
}

@Composable
fun DetectionBox(
    resultImageState:Bitmap?,
    resultDescriptionState:String?,
    onClickButton: ()->Unit){
    Scaffold(containerColor = DefaultBackground) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            Column(
                Modifier
                    .fillMaxSize()
                    .offset(y = (-120).dp)
                    .padding(horizontal = 10.dp)) {
                if (resultImageState!=null){
                    Image(
                        modifier = Modifier
                            .weight(0.6f)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        bitmap = resultImageState.asImageBitmap(),
                        contentDescription = "resultImage")
                    if (resultDescriptionState != null){
                        Text(
                            modifier = Modifier.weight(0.4f),
                            text = resultDescriptionState,
                            color = Color.White)
                    }
                }

            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                onClick = onClickButton,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally),
                        painter = painterResource(id = R.drawable.ic_detection),
                        contentDescription = "Detect Button")
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = stringResource(id = R.string.detect_screen))
                }
            }
        }
    }
}


