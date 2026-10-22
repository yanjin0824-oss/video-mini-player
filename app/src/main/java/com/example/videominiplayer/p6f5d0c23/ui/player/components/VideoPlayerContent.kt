package com.example.videominiplayer.p6f5d0c23.ui.player.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.videominiplayer.p6f5d0c23.R
import com.example.videominiplayer.p6f5d0c23.domain.model.PlaybackStatus
import com.example.videominiplayer.p6f5d0c23.ui.player.VideoPlayerEvent
import com.example.videominiplayer.p6f5d0c23.ui.player.VideoPlayerUiState
import com.pico.spatial.ui.design.Button
import com.pico.spatial.ui.design.ButtonDefaults
import com.pico.spatial.ui.design.Icon
import com.pico.spatial.ui.design.IconButton
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.design.Text
import com.pico.spatial.ui.design.TextField

@Composable
fun VideoPlayerContent(
    state: VideoPlayerUiState,
    onEvent: (VideoPlayerEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .absolutePadding(left = 24.dp, top = 24.dp, right = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (state.status == PlaybackStatus.Ready || state.status == PlaybackStatus.Error) {
            UrlEntryRow(state = state, onEvent = onEvent)
        }

        VideoSurface(
            source = state.source,
            status = state.status,
            message = state.message,
            onEvent = onEvent,
            modifier = Modifier.fillMaxWidth(),
        )

        if (state.status == PlaybackStatus.Playing || state.status == PlaybackStatus.Paused) {
            PlaybackControls(state = state, onEvent = onEvent)
        }
    }
}

@Composable
private fun UrlEntryRow(
    state: VideoPlayerUiState,
    onEvent: (VideoPlayerEvent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = state.urlInput,
            onValueChange = { onEvent(VideoPlayerEvent.UpdateUrl(it)) },
            modifier = Modifier.weight(1f),
            placeholder = { Text("粘贴视频网址…") },
            singleLine = true,
            clearable = true,
        )
        Button(
            onClick = { onEvent(VideoPlayerEvent.Load) },
            size = ButtonDefaults.Regular,
        ) {
            Text("播放")
        }
    }
}

@Composable
private fun PlaybackControls(
    state: VideoPlayerUiState,
    onEvent: (VideoPlayerEvent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (state.status == PlaybackStatus.Playing) {
            IconButton(
                onClick = { onEvent(VideoPlayerEvent.Pause) },
                size = ButtonDefaults.Regular,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_player_pause),
                    contentDescription = "暂停",
                )
            }
        } else {
            IconButton(
                onClick = { onEvent(VideoPlayerEvent.Resume) },
                size = ButtonDefaults.Regular,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_player_play),
                    contentDescription = "播放",
                )
            }
        }

        Text(
            text = state.source?.title.orEmpty(),
            modifier = Modifier.weight(1f),
            style = PicoTheme.typography.bodyMedium,
            color = PicoTheme.colorScheme.labelPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        IconButton(
            onClick = { onEvent(VideoPlayerEvent.EnterNewUrl) },
            size = ButtonDefaults.Regular,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_link),
                contentDescription = "换一个网址",
            )
        }
    }
}
