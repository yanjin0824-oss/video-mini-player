package com.example.videominiplayer.p6f5d0c23.data.repository

import com.example.videominiplayer.p6f5d0c23.domain.model.VideoSource
import java.net.URI

class DefaultVideoSourceRepository : VideoSourceRepository {
    override fun validate(rawUrl: String): VideoSourceResult {
        val normalized = rawUrl.trim()
        if (normalized.isEmpty()) {
            return VideoSourceResult.Invalid("请输入视频网址")
        }

        val uri = runCatching { URI(normalized) }.getOrNull()
            ?: return VideoSourceResult.Invalid("视频网址格式不正确")
        if (uri.scheme !in setOf("http", "https") || uri.host.isNullOrBlank()) {
            return VideoSourceResult.Invalid("请输入以 http:// 或 https:// 开头的视频网址")
        }

        val title = uri.path
            .substringAfterLast('/')
            .takeIf { it.isNotBlank() }
            ?: uri.host
        return VideoSourceResult.Valid(VideoSource(url = normalized, title = title))
    }
}
