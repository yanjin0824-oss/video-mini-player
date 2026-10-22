package com.example.videominiplayer.p6f5d0c23.ui.player

import androidx.lifecycle.ViewModel
import com.example.videominiplayer.p6f5d0c23.data.repository.VideoSourceResult
import com.example.videominiplayer.p6f5d0c23.domain.model.PlaybackStatus
import com.example.videominiplayer.p6f5d0c23.domain.usecase.PrepareVideoUrlUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VideoPlayerViewModel(
    private val prepareVideoUrl: PrepareVideoUrlUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(VideoPlayerUiState())
    val state: StateFlow<VideoPlayerUiState> = _state.asStateFlow()

    fun onEvent(event: VideoPlayerEvent) {
        when (event) {
            is VideoPlayerEvent.UpdateUrl -> _state.update {
                it.copy(urlInput = event.value, message = "粘贴视频网址，点击「播放」")
            }
            VideoPlayerEvent.Load -> loadVideo()
            VideoPlayerEvent.Pause -> _state.update {
                if (it.status == PlaybackStatus.Playing) it.copy(status = PlaybackStatus.Paused) else it
            }
            VideoPlayerEvent.Resume -> _state.update {
                if (it.status == PlaybackStatus.Paused) it.copy(status = PlaybackStatus.Playing) else it
            }
            VideoPlayerEvent.EnterNewUrl -> _state.update {
                it.copy(source = null, status = PlaybackStatus.Ready, message = "粘贴视频网址，点击「播放」")
            }
            VideoPlayerEvent.PlaybackReady -> _state.update {
                if (it.status == PlaybackStatus.Loading) it.copy(status = PlaybackStatus.Playing) else it
            }
            is VideoPlayerEvent.PlaybackFailed -> _state.update {
                it.copy(
                    status = PlaybackStatus.Error,
                    message = "无法播放该视频，请检查网址或媒体格式",
                )
            }
        }
    }

    private fun loadVideo() {
        when (val result = prepareVideoUrl(_state.value.urlInput)) {
            is VideoSourceResult.Valid -> _state.update {
                it.copy(source = result.source, status = PlaybackStatus.Loading, message = "正在加载视频…")
            }
            is VideoSourceResult.Invalid -> _state.update {
                it.copy(source = null, status = PlaybackStatus.Error, message = result.message)
            }
        }
    }
}
