package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BaseGameScreen(
    // Верхняя панель - 3 блока
    topLeftContent: @Composable () -> Unit = {},
    topCenterContent: @Composable () -> Unit = {},
    topRightContent: @Composable () -> Unit = {},

    // Центральная часть - ОСНОВНОЙ контент + ДОПОЛНИТЕЛЬНЫЙ
    centerMainContent: @Composable () -> Unit = {},
    centerAdditionalContent: @Composable () -> Unit = {},

    // Нижняя панель - 3 блока
    bottomLeftContent: @Composable () -> Unit = {},
    bottomCenterContent: @Composable () -> Unit = {},
    bottomRightContent: @Composable () -> Unit = {},

    backgroundColor: Color = Color(0xFF1E1E1E)
) {
    // НАСЛЕДУЕМ BaseScreen
    BaseScreen(
        backgroundColor = backgroundColor,
        topContent = {
            // ВЕРХ - 3 блока
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    topLeftContent()
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    topCenterContent()
                }

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    topRightContent()
                }
            }
        },
        centerContent = {
            // ЦЕНТР - ОСНОВНОЙ контент + ДОПОЛНИТЕЛЬНЫЙ
            Column(modifier = Modifier.fillMaxSize()) {
                // ОСНОВНОЙ контент - 87.5% центра
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.85f),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(225f / 294f)
                            .background(Color.DarkGray, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        centerMainContent()
                    }
                }

                // ДОПОЛНИТЕЛЬНЫЙ контент - 12.5% центра
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.15f),
                    contentAlignment = Alignment.Center
                ) {
                    centerAdditionalContent()
                }
            }
        },
        bottomContent = {
            // НИЗ - 3 блока
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Box(modifier = Modifier.weight(1f)) { bottomLeftContent() }
                Box(modifier = Modifier.weight(1f)) { bottomCenterContent() }
                Box(modifier = Modifier.weight(1f)) { bottomRightContent() }
            }
        }
    )
}