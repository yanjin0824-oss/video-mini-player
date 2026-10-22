package com.example.videominiplayer.p6f5d0c23.ui.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.videominiplayer.p6f5d0c23.domain.model.PlaybackStatus
import com.example.videominiplayer.p6f5d0c23.domain.model.VideoSource
import com.example.videominiplayer.p6f5d0c23.ui.player.VideoPlayerEvent
import com.example.videominiplayer.p6f5d0c23.ui.theme.MiniPlayerColors
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.design.Text

@Composable
fun VideoSurface(
    source: VideoSource?,
    status: PlaybackStatus,
    message: String,
    onEvent: (VideoPlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(16f / 9f)
            .clip(RoundedCornerShape(16.dp))
            // design-style: design-surface screen-video
            .background(MiniPlayerColors.screenSurface),
        contentAlignment = Alignment.Center,
    ) {
        if (source != null) {
            Media3Player(
                source = source,
                shouldPlay = status == PlaybackStatus.Loading || status == PlaybackStatus.Playing,
                onEvent = onEvent,
                modifier = Modifier.fillMaxSize(),
            )
        }

        if (source == null || status == PlaybackStatus.Loading || status == PlaybackStatus.Error) {
            Text(
                text = message,
                style = PicoTheme.typography.bodyMedium,
                color = PicoTheme.colorScheme.labelPrimaryLight,
            )
        }
    }
}

@Composable
private fun Media3Player(
    source: VideoSource,
    shouldPlay: Boolean,
    onEvent: (VideoPlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val currentOnEvent by rememberUpdatedState(onEvent)
    val player = remember(context) { ExoPlayer.Builder(context).build() }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    currentOnEvent(VideoPlayerEvent.PlaybackReady)
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                currentOnEvent(VideoPlayerEvent.PlaybackFailed(error.errorCodeName))
            }
        }
        player.addListener(listener)
        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    LaunchedEffect(player, source.url) {
        player.setMediaItem(MediaItem.fromUri(source.url))
        player.prepare()
    }

    LaunchedEffect(player, shouldPlay) {
        player.playWhenReady = shouldPlay
    }

    AndroidView(
        modifier = modifier,
        factory = { viewContext ->
            PlayerView(viewContext).apply {
                useController = false
                this.player = player
            }
        },
        update = { it.player = player },
    )
}
