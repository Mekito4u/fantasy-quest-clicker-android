package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import com.example.fantasyquestclicker.ui.theme.components.HealthBar
import com.example.fantasyquestclicker.ui.theme.components.PlayerTimer
import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel

@Composable
fun BattleScreen(
    currentScreen: String = "battle",
    onScreenChange: (String) -> Unit = { _ -> },
    onBackClick: () -> Unit = {}
) {
    val viewModel: BattleViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val enemy by viewModel.currentEnemy.collectAsState()
    val player by viewModel.player.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPlayerProgress()
    }

    BaseGameScreen(
        player = player,
        showNavigationArrows = false,
        currentScreen = currentScreen,
        onScreenChange = onScreenChange,
        onBackClick = onBackClick,
        onCenterClick = { viewModel.attackEnemy() },

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
                    .fillMaxWidth(1f)
                    .aspectRatio(225f / 294f),
                contentAlignment = Alignment.Center
            ) {
                Text(enemy.imageRes, fontSize = 100.sp, color = Color.White)
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
    )
}