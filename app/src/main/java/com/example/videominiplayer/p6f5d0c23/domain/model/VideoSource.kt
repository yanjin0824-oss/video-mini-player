package com.example.videominiplayer.p6f5d0c23.domain.model

data class VideoSource(
    val url: String,
    val title: String,
)

enum class PlaybackStatus {
    Ready,
    Loading,
    Playing,
    Paused,
    Error,
}
