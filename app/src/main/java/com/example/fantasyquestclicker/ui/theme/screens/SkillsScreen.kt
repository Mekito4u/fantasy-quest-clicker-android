package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fantasyquestclicker.di.ViewModelFactory
import com.example.fantasyquestclicker.domain.models.SkillType
import com.example.fantasyquestclicker.domain.models.getCurrentSkillValue
import com.example.fantasyquestclicker.domain.utils.NumberFormatter.formatNumber
import com.example.fantasyquestclicker.ui.theme.viewmodels.SkillsViewModel

@Composable
fun SkillsScreen(
    currentScreen: String = "skills",
    onScreenChange: (String) -> Unit = { _ -> },
    onBackClick: () -> Unit = {}
) {
    val viewModel: SkillsViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val player by viewModel.player.collectAsState()
    var selectedSkill by remember { mutableStateOf(SkillType.ATTACK) }

    LaunchedEffect(Unit) {
        viewModel.loadPlayerProgress()
    }

    // ФУНКЦИИ ДЛЯ СТРЕЛОК
    val onLeftArrowClick = {
        selectedSkill = when (selectedSkill) {
            SkillType.ATTACK -> SkillType.CRITICAL
            SkillType.TIME -> SkillType.ATTACK
            SkillType.CRITICAL -> SkillType.TIME
        }
    }

    val onRightArrowClick = {
        selectedSkill = when (selectedSkill) {
            SkillType.ATTACK -> SkillType.TIME
            SkillType.TIME -> SkillType.CRITICAL
            SkillType.CRITICAL -> SkillType.ATTACK
        }
    }

    BaseGameScreen(
        player = player,
        showNavigationArrows = true,
        onLeftArrowClick = onLeftArrowClick,
        onRightArrowClick = onRightArrowClick,
        currentScreen = currentScreen,
        onScreenChange = onScreenChange,
        onBackClick = onBackClick,

        // ЦЕНТРАЛЬНАЯ ЧАСТЬ - ЗАГОЛОВОК
        centerAdditionalContentTop = {
            Text(
                "НАВЫКИ",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },

        // ОСНОВНОЙ КОНТЕНТ - ИНФО О НАВЫКЕ (БЕЗ СТРЕЛОК)
        centerMainContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.02f))
                // НАЗВАНИЕ НАВЫКА
                Text(
                    selectedSkill.displayName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.25f))
                // ОПИСАНИЕ
                Text(
                    selectedSkill.description,
                    color = Color.Gray,
                    fontSize = 19.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                // ТЕКУЩЕЕ ЗНАЧЕНИЕ
                Text(
                    "Текущее: ${getCurrentSkillValue(player, selectedSkill)}",
                    color = Color(0xFF4FC3F7),
                    fontSize = 20.sp,
                )

                // ЦЕНА ПРОКАЧКИ
                Text(
                    "Цена: ${formatNumber(selectedSkill.getUpgradeCost(player))} золота",
                    color = Color(0xFFFFA726),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.02f))
            }
        },

        // КНОПКА ПРОКАЧКИ
        centerAdditionalContentBottom = {
            Button(
                onClick = { viewModel.upgradeSkill(selectedSkill) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(70.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (player.gold >= selectedSkill.getUpgradeCost(player)) {
                        Color(0xFF4CAF50)
                    } else {
                        Color(0xFF666666)
                    }
                ),
                enabled = player.gold >= selectedSkill.getUpgradeCost(player)
            ) {
                Text(
                    "ПРОКАЧАТЬ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}