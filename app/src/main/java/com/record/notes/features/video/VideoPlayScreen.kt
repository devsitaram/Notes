package com.record.notes.features.video

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

//@Composable
//fun VideoPlayScreen() {
//    val context = LocalContext.current
//    val url = "https://www.youtube.com/watch?v=AR2SAxNlXJA" // Replace with a valid video URL
//    val exoPlayer: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
//    val mediaItem = MediaItem.fromUri(Uri.parse(url))
//    exoPlayer.setMediaItem(mediaItem)
//    val playerView = StyledPlayerView(context)
//    playerView.player = exoPlayer
//
//    Surface(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier
//            .fillMaxWidth()
//            .padding(20.dp)) {
//            @SuppressLint("OpaqueUnitKey")
//            @Composable
//            fun VideoPlayScreen() {
//
//                DisposableEffect(AndroidView(factory = { playerView })) {
//                    exoPlayer.prepare()
//                    exoPlayer.playWhenReady = true
//                    onDispose {
//                        exoPlayer.release()
//                    }
//                }
//            }
//        }
//    }
//}

//@Composable
//fun VideoPlayScreen() {
//    val context = LocalContext.current
//    val videoUrl = "https://www.youtube.com/watch?v=AR2SAxNlXJA"
//
//    videoUrl.let { actualVideoUrl ->
//        val exoPlayer = SimpleExoPlayer.Builder(context).build()
//        val mediaItem = MediaItem.fromUri(videoUrl)
//        exoPlayer.setMediaItem(mediaItem)
//
//        val playerView = StyledPlayerView(context)
//        playerView.player = exoPlayer
//
//        DisposableEffect(AndroidView(factory = { playerView })) {
//            exoPlayer.prepare()
//            exoPlayer.playWhenReady = true
//            onDispose {
//                exoPlayer.release()
//            }
//        }
//    }
//}

@Composable
fun VideoPlayScreen() {
    val context = LocalContext.current
//    val videoUrl = "https://www.youtube.com/watch?v=AR2SAxNlXJA"
    val videoKey = "watch?v=AR2SAxNlXJA"

    var actualVideoUrl by remember(videoKey) {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(videoKey) {
        val extractedUrl = extractYouTubeVideoUrl(videoKey)
        actualVideoUrl = extractedUrl
    }

    actualVideoUrl?.let { url ->
        val exoPlayer = SimpleExoPlayer.Builder(context).build()
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem)

        val playerView = StyledPlayerView(context)
        playerView.player = exoPlayer

        DisposableEffect(AndroidView(factory = { playerView })) {
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
            onDispose {
                exoPlayer.release()
            }
        }
    } ?: run {
        Text("Error loading video")
    }
}

suspend fun extractYouTubeVideoUrl(videoKey: String): String? {
    val client = OkHttpClient()
    val request = Request.Builder()
//        .url("https://www.youtube.com/$videoKey")
        .url("https://www.youtube.com/oembed?url=$videoKey&format=json")
        .build()

    return try {
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        if (!response.isSuccessful) {
            return null
        }

        val responseBody = response.body.string()
        val json = JSONObject(responseBody)
        val videoUrl = json.optString(videoKey)

        videoUrl.takeIf { it.isNotBlank()  }
    } catch (e: IOException) {
        null
    }
}