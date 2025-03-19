package com.stand.screens.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.stand.camera.CameraPreview
import com.stand.screens.home.HomeState
import com.stand.screens.home.HomeViewModel
import java.io.File
import java.io.FileOutputStream

@Composable
fun CameraScreen(
    homeViewModel: HomeViewModel
) {

    Camera(
        onPhotoTaken = { uri ->
            homeViewModel.navigateBackToHome(uri)
        }
    )
}

@Composable
fun Camera(
    onPhotoTaken: (Uri) -> Unit
) {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
            )
        }
    }

    CameraInteractions(
        controller = controller,
        takePhoto = {
            takePhoto(controller, context, onPhotoTaken)
        },
    )
}

fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Uri) -> Unit,
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }
                val rotatedBitmap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0, 0, image.width, image.height,
                    matrix, true
                )

                val photoUri = savePhotoToExternalStorage(rotatedBitmap, context)

                if (photoUri != null) {
                    onPhotoTaken(photoUri)
                }

                image.close()
            }

        }
    )
}

private fun savePhotoToExternalStorage(bitmap: Bitmap, context: Context): Uri? {
    val directory = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
        "StandApp"
    )
    if (!directory.exists()) {
        directory.mkdirs()
    }

    val fileName = "photo_${System.currentTimeMillis()}.jpg"
    val file = File(directory, fileName)

    try {
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val photoUri = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            file.absolutePath,
            file.name,
            null
        )
        return Uri.parse(photoUri)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

@Composable
fun CameraInteractions(
    controller: LifecycleCameraController,
    takePhoto: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CameraPreview(
            controller = controller,
            modifier = Modifier
                .fillMaxSize()
        )

        IconButton(
            onClick = {
                controller.cameraSelector =
                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else CameraSelector.DEFAULT_BACK_CAMERA
            },
            modifier = Modifier
                .offset(16.dp, 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Switch camera"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Button(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(90.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = { takePhoto() }
            ) {
            }
        }
    }
}

@Composable
@Preview
fun PrevBut() {
    Button(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(90.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        onClick = { TODO() }
    ) {

    }
}
