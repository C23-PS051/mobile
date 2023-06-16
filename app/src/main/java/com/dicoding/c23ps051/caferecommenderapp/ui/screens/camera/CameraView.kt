package com.dicoding.c23ps051.caferecommenderapp.ui.screens.camera

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.dicoding.c23ps051.caferecommenderapp.R
import com.dicoding.c23ps051.caferecommenderapp.ui.components.BackPressHandler
import com.dicoding.c23ps051.caferecommenderapp.ui.event.BackPress
import com.dicoding.c23ps051.caferecommenderapp.ui.theme.White
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

@Composable
fun CameraView(
    cameraSelector: CameraSelector,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onSwitchCamera: () -> Unit,
    onError: (ImageCaptureException) -> Unit,
    navigateUp: () -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }

    LaunchedEffect(cameraSelector) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )

        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier
                .padding(top = 10.dp, end = 10.dp)
                .align(Alignment.TopEnd),
            onClick = onSwitchCamera
        ) {
            Icon(
                painter = painterResource(id = R.drawable.switch_camera),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(id = R.string.switch_camera_lens),
                modifier = Modifier
                    .size(100.dp)
                    .padding(1.dp)
            )
        }

        IconButton(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .size(56.dp)
                .align(Alignment.BottomCenter),
            onClick = {
                takePhoto(
                    context = context,
                    imageCapture = imageCapture,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.capture),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = stringResource(id = R.string.take_picture),
                modifier = Modifier
                    .size(100.dp)
                    .border(2.dp, White, CircleShape)
            )
        }
    }

    BackPressHandler(
        backPressState = BackPress.Idle,
        toggleBackPressState = {}
    ) {
        navigateUp()
    }
}

private fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = createFile(context.applicationContext as Application)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createFile(application: Application): File {
    val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
        File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
    }

    val outputDirectory = if (
        mediaDir != null && mediaDir.exists()
    ) mediaDir else application.filesDir

    return File(outputDirectory, "$timeStamp.jpg")
}