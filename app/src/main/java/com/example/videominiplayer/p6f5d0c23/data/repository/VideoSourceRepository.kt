package com.example.videominiplayer.p6f5d0c23.data.repository

import com.example.videominiplayer.p6f5d0c23.domain.model.VideoSource

sealed interface VideoSourceResult {
    data class Valid(val source: VideoSource) : VideoSourceResult
    data class Invalid(val message: String) : VideoSourceResult
}

interface VideoSourceRepository {
    fun validate(rawUrl: String): VideoSourceResult
}
