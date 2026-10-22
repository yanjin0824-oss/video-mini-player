package com.example.videominiplayer.p6f5d0c23.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.videominiplayer.p6f5d0c23.data.repository.DefaultVideoSourceRepository
import com.example.videominiplayer.p6f5d0c23.domain.usecase.PrepareVideoUrlUseCase

class VideoPlayerViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(VideoPlayerViewModel::class.java))
        return VideoPlayerViewModel(
            prepareVideoUrl = PrepareVideoUrlUseCase(DefaultVideoSourceRepository()),
        ) as T
    }
}
