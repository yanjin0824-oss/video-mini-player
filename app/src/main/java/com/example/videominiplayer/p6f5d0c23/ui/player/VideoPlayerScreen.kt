package com.example.videominiplayer.p6f5d0c23.ui.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videominiplayer.p6f5d0c23.ui.player.components.VideoPlayerContent

@Composable
fun VideoPlayerScreen(
    modifier: Modifier = Modifier,
    viewModel: VideoPlayerViewModel = viewModel(factory = VideoPlayerViewModelFactory()),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    VideoPlayerContent(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}
