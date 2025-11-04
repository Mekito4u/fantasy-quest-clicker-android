package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fantasyquestclicker.di.ViewModelFactory
import com.example.fantasyquestclicker.domain.utils.NumberFormatter.formatNumber
import com.example.fantasyquestclicker.ui.theme.components.HealthBar
import com.example.fantasyquestclicker.ui.theme.components.PlayerTimer
import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel

@Composable
fun BattleScreen(
    onNavigateToSkills: () -> Unit = {},
    onNavigateToQuests: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val viewModel: BattleViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current)
    )
    val player by viewModel.player.collectAsState()
    val enemy by viewModel.currentEnemy.collectAsState()

    BaseGameScreen(
        onCenterClick = { viewModel.attackEnemy() },

        // ВЕРХНЯЯ ПАНЕЛЬ - 3 блока
        topLeftContent = {
            TextButton(onClick = onBackClick,
            ) {
                Text(
                    "Назад",
                    color = Color.White,
                    fontSize = 22.sp
                )
            }
        },
        topCenterContent = {
            Text(
                text = "Золото: ${formatNumber(player.gold)}",
                color = Color(0xFFFFD700),
                fontSize = 22.sp
            )
        },
        topRightContent = {
            Text(
                "Стадия: ${player.currentStage}",
                color = Color.White,
                fontSize = 22.sp
            )
        },

        // ЦЕНТРАЛЬНАЯ ЧАСТЬ - PLAYER HEALTH BAR
        centerAdditionalContentTop = {
            PlayerTimer(
                currentTime = player.currentTime,
            )
        },

        // ЦЕНТРАЛЬНАЯ ЧАСТЬ - ВРАГ
        centerMainContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(225f / 294f),
                contentAlignment = Alignment.Center
            ) {
                Text("🦇", fontSize = 60.sp, color = Color.White)
            }

        },

        // ЦЕНТРАЛЬНАЯ ЧАСТЬ - HEALTH BAR
        centerAdditionalContentBottom = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    enemy.name,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                HealthBar(
                    currentHealth = enemy.currentHealth,
                    maxHealth = enemy.maxHealth,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                )
            }
        },

        // НИЖНЯЯ ПАНЕЛЬ - навигация
        bottomLeftContent = {
            Button(
                onClick = { /* Уже на экране боя */ },
                modifier = Modifier
                    .aspectRatio(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray,
                    disabledContainerColor = Color(0xFF363636),
                    disabledContentColor = Color(0xCCCCCCCC)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp
                ),
                enabled = false,
            ) {
                Text("Бой", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        bottomCenterContent = {
            Button(
                onClick = onNavigateToQuests,
                modifier = Modifier
                    .aspectRatio(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
            ) {
                Text("Квесты", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        },
        bottomRightContent = {
            Button(
                onClick = onNavigateToSkills,
                modifier = Modifier
                    .aspectRatio(1f),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.DarkGray
                ),
            ) {
                Text("Навыки", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    )
}