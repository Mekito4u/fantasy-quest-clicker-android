package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BaseScreen(
    topContent: @Composable () -> Unit = {},
    centerContent: @Composable () -> Unit = {},
    bottomContent: @Composable () -> Unit = {},
    backgroundColor: Color = Color(0xFF1E1E1E)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ВЕРХ - 10%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.1f),
                contentAlignment = Alignment.Center
            ) {
                topContent()
            }

            // ЦЕНТР - 70%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f),
                contentAlignment = Alignment.Center
            ) {
                centerContent()
            }

            // НИЗ - 20%
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                bottomContent()
            }
        }
    }
}