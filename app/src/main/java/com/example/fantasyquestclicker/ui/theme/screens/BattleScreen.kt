package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel
import com.example.fantasyquestclicker.ui.theme.components.HealthBar

@Composable
fun BattleScreen() {
    val viewModel: BattleViewModel = viewModel()
    val player by viewModel.player.collectAsState()
    val enemy by viewModel.currentEnemy.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            // 1. ВЕРХНЯЯ ПАНЕЛЬ - 10% высоты
            Text("Уровень: ${player.level}")
            Text("Золото: ${player.gold}")

            Spacer(modifier = Modifier.height(32.dp))

            // 2. ЦЕНТРАЛЬНАЯ ЧАСТЬ - 70% высоты
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        viewModel.attackEnemy()
                    }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ВЕРХ ЦЕНТРАЛЬНОЙ ЧАСТИ - 87.5% высоты
                    Box(
                        modifier = Modifier
                            .weight(0.875f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        // ВРЕМЕННАЯ ЗАГЛУШКА
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(240.dp)
                                .background(Color(0xFF333333), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "🦇",
                                fontSize = 60.sp,
                                color = Color.White
                            )
                        }
                    }

                    // НИЗ ЦЕНТРАЛЬНОЙ ЧАСТИ - 12.5% высоты
                    Column(
                        modifier = Modifier
                            .weight(0.125f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            "Враг: ${enemy.name}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        HealthBar(
                            currentHealth = enemy.currentHealth,
                            maxHealth = enemy.maxHealth,
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(70.dp)
                        )
                    }
                }
            }

            // 3. НИЖНЯЯ ЧАСТЬ - 20% высоты
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                verticalArrangement = Arrangement.Bottom
            ) {

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
