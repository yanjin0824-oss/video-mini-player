package com.example.videominiplayer.p6f5d0c23.domain.usecase

import com.example.videominiplayer.p6f5d0c23.data.repository.VideoSourceRepository
import com.example.videominiplayer.p6f5d0c23.data.repository.VideoSourceResult

class PrepareVideoUrlUseCase(
    private val repository: VideoSourceRepository,
) {
    operator fun invoke(rawUrl: String): VideoSourceResult = repository.validate(rawUrl)
}
