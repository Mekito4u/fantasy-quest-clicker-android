package com.example.fantasyquestclicker.ui.theme.viewmodels

import com.example.fantasyquestclicker.domain.models.Quest
import com.example.fantasyquestclicker.domain.models.QuestType
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import com.example.fantasyquestclicker.domain.utils.QuestGenerator

// ViewModel для экрана квестов
class QuestsViewModel(
    gameRepository: GameRepository
) : BaseGameViewModel(gameRepository) {
    // Забрать награду за квест
    fun claimReward(quest: Quest) {
        val updatedPlayer = if (quest.type == QuestType.KILL_COUNT) {
            _playerState.value.copy(
                gold = _playerState.value.gold + quest.reward,
                totalKillsEnemy = 0
            )
        } else {
            _playerState.value.copy(
                gold = _playerState.value.gold + quest.reward
            )
        }


        _playerState.value = updatedPlayer
        savePlayerProgress()
        QuestGenerator.replaceQuest(updatedPlayer, quest.type)
    }
}