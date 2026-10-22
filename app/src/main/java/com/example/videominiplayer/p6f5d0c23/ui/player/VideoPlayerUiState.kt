package com.example.videominiplayer.p6f5d0c23.ui.player

import com.example.videominiplayer.p6f5d0c23.domain.model.PlaybackStatus
import com.example.videominiplayer.p6f5d0c23.domain.model.VideoSource

data class VideoPlayerUiState(
    val urlInput: String = "",
    val source: VideoSource? = null,
    val status: PlaybackStatus = PlaybackStatus.Ready,
    val message: String = "粘贴视频网址，点击「播放」",
)

sealed interface VideoPlayerEvent {
    data class UpdateUrl(val value: String) : VideoPlayerEvent
    data object Load : VideoPlayerEvent
    data object Pause : VideoPlayerEvent
    data object Resume : VideoPlayerEvent
    data object EnterNewUrl : VideoPlayerEvent
    data object PlaybackReady : VideoPlayerEvent
    data class PlaybackFailed(val reason: String? = null) : VideoPlayerEvent
}
