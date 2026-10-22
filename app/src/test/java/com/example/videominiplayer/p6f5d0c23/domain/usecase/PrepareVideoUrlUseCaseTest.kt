package com.example.videominiplayer.p6f5d0c23.domain.usecase

import com.example.videominiplayer.p6f5d0c23.data.repository.DefaultVideoSourceRepository
import com.example.videominiplayer.p6f5d0c23.data.repository.VideoSourceResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PrepareVideoUrlUseCaseTest {
    private val useCase = PrepareVideoUrlUseCase(DefaultVideoSourceRepository())

    @Test
    fun validHttpsUrlReturnsVideoSource() {
        val result = useCase("https://cdn.example.com/videos/focus.mp4")

        assertTrue(result is VideoSourceResult.Valid)
        assertEquals("focus.mp4", (result as VideoSourceResult.Valid).source.title)
    }

    @Test
    fun unsupportedSchemeReturnsValidationMessage() {
        val result = useCase("file:///sdcard/movie.mp4")

        assertTrue(result is VideoSourceResult.Invalid)
    }
}
