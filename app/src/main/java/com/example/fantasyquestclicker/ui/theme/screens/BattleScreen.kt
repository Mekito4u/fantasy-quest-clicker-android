package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

/**
 * Главный экран боя. Отображает врага, полосу здоровья, таймер и обрабатывает атаки.
 * @param currentScreen Идентификатор текущего активного экрана (для подсветки кнопки навигации).
 * @param onScreenChange Callback-функция для переключения между экранами (бой/квесты/навыки).
 */
@Composable
fun BattleScreen(
    currentScreen: String = "battle",
    onScreenChange: (String) -> Unit = { _ -> },
) {
    // Получение ViewModel через фабрику для внедрения зависимостей
    val viewModel: BattleViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))

    // Подписка на состояние текущего врага
    val enemy by viewModel.currentEnemy.collectAsState()
    // Подписка на состояние игрока
    val player by viewModel.player.collectAsState()

    // Однократная загрузка прогресса при первом показе экрана
    LaunchedEffect(Unit) {
        viewModel.loadPlayerProgress()
    }

    // Использование базового макета игры с настройкой под экран боя
    BaseGameScreen(
        player = player,
        showNavigationArrows = false,
        currentScreen = currentScreen,
        onScreenChange = onScreenChange,
        // Обработка нажатия по центру экрана как атаки
        onCenterClick = { viewModel.attackEnemy() },

        // Верхняя дополнительная область: таймер игрока
        centerAdditionalContentTop = {
            PlayerTimer(
                currentTime = player.currentTime,
            )
        },

        // Основная центральная область: изображение врага
        centerMainContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .aspectRatio(225f / 294f),
                contentAlignment = Alignment.Center
            ) {
                // Враг отображается как текстовый эмодзи (например, "👹")
                Text(enemy.imageRes, fontSize = 150.sp, color = Color.White)
            }
        },

        // Нижняя дополнительная область: имя и полоса здоровья врага
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