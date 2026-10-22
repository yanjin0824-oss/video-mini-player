package com.example.videominiplayer.p6f5d0c23.ui.player

import com.example.videominiplayer.p6f5d0c23.data.repository.DefaultVideoSourceRepository
import com.example.videominiplayer.p6f5d0c23.domain.model.PlaybackStatus
import com.example.videominiplayer.p6f5d0c23.domain.usecase.PrepareVideoUrlUseCase
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VideoPlayerViewModelTest {
    private fun createViewModel() = VideoPlayerViewModel(
        PrepareVideoUrlUseCase(DefaultVideoSourceRepository()),
    )

    @Test
    fun initialStateIsReady() {
        val state = createViewModel().state.value

        assertEquals(PlaybackStatus.Ready, state.status)
        assertNull(state.source)
    }

    @Test
    fun validUrlMovesFromLoadingToPlaying() {
        val viewModel = createViewModel()
        viewModel.onEvent(VideoPlayerEvent.UpdateUrl("https://cdn.example.com/work.mp4"))
        viewModel.onEvent(VideoPlayerEvent.Load)

        assertEquals(PlaybackStatus.Loading, viewModel.state.value.status)
        assertEquals("work.mp4", viewModel.state.value.source?.title)

        viewModel.onEvent(VideoPlayerEvent.PlaybackReady)
        assertEquals(PlaybackStatus.Playing, viewModel.state.value.status)
    }

    @Test
    fun pauseAndResumePreserveCurrentSource() {
        val viewModel = createViewModel()
        viewModel.onEvent(VideoPlayerEvent.UpdateUrl("https://cdn.example.com/work.mp4"))
        viewModel.onEvent(VideoPlayerEvent.Load)
        viewModel.onEvent(VideoPlayerEvent.PlaybackReady)

        viewModel.onEvent(VideoPlayerEvent.Pause)
        assertEquals(PlaybackStatus.Paused, viewModel.state.value.status)

        viewModel.onEvent(VideoPlayerEvent.Resume)
        assertEquals(PlaybackStatus.Playing, viewModel.state.value.status)
        assertEquals("https://cdn.example.com/work.mp4", viewModel.state.value.source?.url)
    }

    @Test
    fun invalidUrlShowsInlineErrorAndNewUrlResets() {
        val viewModel = createViewModel()
        viewModel.onEvent(VideoPlayerEvent.UpdateUrl("not a url"))
        viewModel.onEvent(VideoPlayerEvent.Load)

        assertEquals(PlaybackStatus.Error, viewModel.state.value.status)
        assertNull(viewModel.state.value.source)

        viewModel.onEvent(VideoPlayerEvent.EnterNewUrl)
        assertEquals(PlaybackStatus.Ready, viewModel.state.value.status)
    }
}
