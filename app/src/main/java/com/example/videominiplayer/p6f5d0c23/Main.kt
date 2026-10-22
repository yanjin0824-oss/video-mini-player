package com.example.videominiplayer.p6f5d0c23

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pico.spatial.ui.design.PicoTheme
import com.pico.spatial.ui.foundation.dsl.DefaultWindowContainer
import com.pico.spatial.ui.foundation.dsl.SpatialAppScope
import com.example.videominiplayer.p6f5d0c23.ui.player.VideoPlayerScreen

fun mainApp(scope: SpatialAppScope) =
    with(scope) {
        DefaultWindowContainer {
            PicoTheme {
                VideoPlayerScreen(
                    modifier = Modifier.windowConstraints(
                        minWidth = 360.dp,
                        minHeight = 340.dp,
                        maxWidth = 760.dp,
                        maxHeight = 620.dp,
                    ),
                )
            }
        }
    }
