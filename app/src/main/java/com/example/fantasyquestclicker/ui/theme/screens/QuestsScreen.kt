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

@Composable
fun QuestsScreen(
    currentScreen: String = "quests",
    onScreenChange: (String) -> Unit = { _ -> },
    onBackClick: () -> Unit = {}
) {
    val viewModel: QuestsViewModel = viewModel(factory = ViewModelFactory(LocalContext.current))
    val player by viewModel.player.collectAsState()
    val quests = remember { mutableStateOf(emptyList<Quest>()) }


    LaunchedEffect(Unit) {
        viewModel.loadPlayerProgress()
        quests.value = QuestGenerator.getQuests(player)
    }

    var selectedQuest by remember { mutableStateOf(QuestType.KILL_COUNT) }

    // ФУНКЦИИ ДЛЯ СТРЕЛОК
    val onLeftArrowClick = {
        selectedQuest = when (selectedQuest) {
            QuestType.KILL_COUNT -> QuestType.UPGRADE_SKILLS
            QuestType.STAGE_PROGRESS -> QuestType.KILL_COUNT
            QuestType.GOLD_EARN -> QuestType.STAGE_PROGRESS
            QuestType.TOTAL_KILLS -> QuestType.GOLD_EARN
            QuestType.UPGRADE_SKILLS -> QuestType.TOTAL_KILLS
        }
    }

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
        onBackClick = onBackClick,

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
                    if(selectedQuest == QuestType.KILL_COUNT) {
                        ("${selectedQuest.description}\n${QuestGenerator.getQuest(QuestType.KILL_COUNT)?.targetName}")
                    }
                    else{
                        selectedQuest.description
                    },
                    color = Color.Gray,
                    fontSize = 19.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.2f))
                // Награда
                Text(
                    "Награда: ${getQuest(selectedQuest).reward}",
                    color = Color(0xFFFFA726),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
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
                    targetValue = getQuest(selectedQuest).targetValue,
                    completedCount = getQuestProgress(player, selectedQuest),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.8f)
                )
            }
        }
    )
}