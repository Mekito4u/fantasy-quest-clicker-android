package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.*
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
import com.example.fantasyquestclicker.domain.models.Quest
import com.example.fantasyquestclicker.domain.models.QuestType
import com.example.fantasyquestclicker.domain.utils.QuestGenerator
import com.example.fantasyquestclicker.domain.utils.QuestGenerator.getQuest
import com.example.fantasyquestclicker.domain.utils.QuestGenerator.getQuestProgress
import com.example.fantasyquestclicker.ui.theme.components.QuestBar
import com.example.fantasyquestclicker.ui.theme.viewmodels.QuestsViewModel

// Экран Квестов
@Composable
fun QuestsScreen(
    currentScreen: String = "quests",
    onScreenChange: (String) -> Unit = { _ -> },
) {
    val viewModel: QuestsViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val player by viewModel.player.collectAsState()
    val quests = remember { mutableStateOf(emptyList<Quest>()) }

    LaunchedEffect(Unit) {
        viewModel.loadPlayerProgress()
        quests.value = QuestGenerator.getQuests(player)
    }

    var selectedQuest by remember { mutableStateOf(QuestType.KILL_COUNT) }
    val currentQuest = getQuest(selectedQuest)
    val progress = getQuestProgress(player, selectedQuest)

    // ФУНКЦИИ ДЛЯ СТРЕЛКИ ВЛЕВО
    val onLeftArrowClick = {
        selectedQuest = when (selectedQuest) {
            QuestType.KILL_COUNT -> QuestType.UPGRADE_SKILLS
            QuestType.STAGE_PROGRESS -> QuestType.KILL_COUNT
            QuestType.GOLD_EARN -> QuestType.STAGE_PROGRESS
            QuestType.TOTAL_KILLS -> QuestType.GOLD_EARN
            QuestType.UPGRADE_SKILLS -> QuestType.TOTAL_KILLS
        }
    }

    // ФУНКЦИИ ДЛЯ СТРЕЛКИ ВПРАВО
    val onRightArrowClick = {
        selectedQuest = when (selectedQuest) {
            QuestType.KILL_COUNT -> QuestType.STAGE_PROGRESS
            QuestType.STAGE_PROGRESS -> QuestType.GOLD_EARN
            QuestType.GOLD_EARN -> QuestType.TOTAL_KILLS
            QuestType.TOTAL_KILLS -> QuestType.UPGRADE_SKILLS
            QuestType.UPGRADE_SKILLS -> QuestType.KILL_COUNT
        }
    }

    BaseGameScreen(
        player = player,
        showNavigationArrows = true,
        onLeftArrowClick = onLeftArrowClick,
        onRightArrowClick = onRightArrowClick,
        currentScreen = currentScreen,
        onScreenChange = onScreenChange,

        // ЦЕНТРАЛЬНАЯ ЧАСТЬ - ЗАГОЛОВОК
        centerAdditionalContentTop = {
            Text(
                "КВЕСТЫ",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },

        // ОСНОВНОЙ КОНТЕНТ
        centerMainContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // НАЗВАНИЕ КВЕСТА
                Text(
                    selectedQuest.displayName,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.25f))
                // ОПИСАНИЕ
                Text(
                    text = if (selectedQuest == QuestType.KILL_COUNT) {
                        "${selectedQuest.description}\n${currentQuest?.targetName}"
                    } else {
                        selectedQuest.description
                    },
                    color = Color.Gray,
                    fontSize = 19.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))

                val isCompleted = currentQuest?.let { progress >= it.targetValue } ?: false

                if (isCompleted) {
                    Button(
                        onClick = {
                            currentQuest.let { quest ->
                                viewModel.claimReward(quest)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
                    ) {
                        Text("Забрать ${currentQuest.reward} золота")
                    }
                } else {
                    Text(
                        "Награда: ${currentQuest?.reward}",
                        color = Color(0xFFFFA726),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.02f))
            }
        },

        // QUEST BAR
        centerAdditionalContentBottom = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                QuestBar(
                    targetValue = currentQuest?.targetValue ?: 0,
                    completedCount = getQuestProgress(player, selectedQuest),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                )
            }
        }
    )
}