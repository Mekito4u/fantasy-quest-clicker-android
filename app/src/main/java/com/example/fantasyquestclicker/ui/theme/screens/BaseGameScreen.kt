package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.utils.NumberFormatter.formatNumber
import com.example.fantasyquestclicker.domain.utils.getBackgroundForStage

@Composable
fun BaseGameScreen(
    player: Player,
    showNavigationArrows: Boolean = false,
    onLeftArrowClick: () -> Unit = {},
    onRightArrowClick: () -> Unit = {},

    currentScreen: String = "battle",
    onScreenChange: (String) -> Unit = {},

    onCenterClick: () -> Unit = {},

    centerAdditionalContentTop: @Composable () -> Unit = {},
    centerMainContent: @Composable () -> Unit = {},
    centerAdditionalContentBottom: @Composable () -> Unit = {},

    backgroundColor: Color = Color(0xFF1E1E1E)
) {
    val backgroundRes = remember(player.currentStage) {
        getBackgroundForStage(player.currentStage)
    }

    BaseScreen(
        backgroundColor = backgroundColor,
        topContent = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "(${player.enemiesDefeated}/10)",
                    color = Color.White,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(0.5f)
                )

                Text(
                    text = when ((player.currentStage - 1) / 5 + 1) {
                        1 -> "Светлая Долина"
                        2 -> "Заброшенная Деревня"
                        3 -> "Врата Руин"
                        4 -> "Мрачный Лес"
                        5 -> "Болота Смерти"
                        else -> "Гнездо Дракона"
                    } + " ${player.currentStage}",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "\uD83E\uDE99: ${formatNumber(player.gold)}",
                    color = Color(0xFFFFD700),
                    fontSize = 22.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(0.5f)
                )
            }
        },
        centerContent = {
            Image(
                painter = painterResource(backgroundRes),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = onCenterClick
                    )
            ) {
                // ВЕРХНИЙ ДОП. КОНТЕНТ (6.25%)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.0625f),
                    contentAlignment = Alignment.Center
                ) {
                    centerAdditionalContentTop()
                }

                // ОСНОВНОЙ КОНТЕНТ (81.25%)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.8125f),
                    contentAlignment = Alignment.Center
                ) {
                    if (showNavigationArrows) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // ЛЕВАЯ СТРЕЛКА
                            TriangleButton(
                                onClick = onLeftArrowClick,
                                direction = TriangleDirection.LEFT,
                                modifier = Modifier
                                    .size(80.dp)
                                    .weight(0.2f)
                            )
                            
                            Spacer(modifier = Modifier.weight(0.015f))
                            
                            // ОСНОВНОЙ КОНТЕНТ
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .aspectRatio(225f / 294f)
                                    .background(Color.DarkGray, RoundedCornerShape(16.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                centerMainContent()
                            }

                            Spacer(modifier = Modifier.weight(0.015f))

                            // ПРАВАЯ СТРЕЛКА
                            TriangleButton(
                                onClick = onRightArrowClick,
                                direction = TriangleDirection.RIGHT,
                                modifier = Modifier
                                    .size(80.dp)
                                    .weight(0.2f)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .aspectRatio(225f / 294f),
                                //.background(Color.DarkGray, RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            centerMainContent()
                        }
                    }
                }

                // НИЖНИЙ ДОП. КОНТЕНТ (12.5%)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.125f),
                    contentAlignment = Alignment.Center
                ) {
                    centerAdditionalContentBottom()
                }
            }
        },
        bottomContent = {
            // НИЖНИЕ КНОПКИ
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = { onScreenChange("battle") },
                        modifier = Modifier.aspectRatio(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentScreen == "battle") Color(0xFF363636) else Color.DarkGray,
                            disabledContainerColor = Color(0xFF363636),
                            disabledContentColor = Color(0xCCCCCCCC)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        ),
                        enabled = currentScreen != "battle"
                    ) {
                        Text("Бой", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = { onScreenChange("quests") },
                        modifier = Modifier.aspectRatio(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentScreen == "quests") Color(0xFF363636) else Color.DarkGray,
                            disabledContainerColor = Color(0xFF363636),
                            disabledContentColor = Color(0xCCCCCCCC)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        ),
                        enabled = currentScreen != "quests"
                    ) {
                        Text("Квесты", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.weight(0.1f))

                Box(modifier = Modifier.weight(1f)) {
                    Button(
                        onClick = { onScreenChange("skills") },
                        modifier = Modifier.aspectRatio(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentScreen == "skills") Color(0xFF363636) else Color.DarkGray,
                            disabledContainerColor = Color(0xFF363636),
                            disabledContentColor = Color(0xCCCCCCCC)
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 0.dp,
                            pressedElevation = 0.dp
                        ),
                        enabled = currentScreen != "skills"
                    ) {
                        Text("Навыки", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    )
}

enum class TriangleDirection {
    LEFT, RIGHT
}

@Composable
private fun TriangleButton(
    onClick: () -> Unit,
    direction: TriangleDirection,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(TriangleShape(direction))
            .background(Color.DarkGray)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.55f)
                .clip(TriangleShape(direction))
                .background(Color.Gray)
        )
    }
}

// ФОРМА ТРЕУГОЛЬНИКА
private class TriangleShape(private val direction: TriangleDirection) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val path = androidx.compose.ui.graphics.Path().apply {
            when (direction) {
                TriangleDirection.LEFT -> {
                    moveTo(size.width, 0f)
                    lineTo(0f, size.height / 2)
                    lineTo(size.width, size.height)
                    close()
                }
                TriangleDirection.RIGHT -> {
                    moveTo(0f, 0f)
                    lineTo(size.width, size.height / 2)
                    lineTo(0f, size.height)
                    close()
                }
            }
        }
        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}