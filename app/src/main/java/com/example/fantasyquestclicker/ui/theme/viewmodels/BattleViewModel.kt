package com.example.fantasyquestclicker.ui.theme.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.fantasyquestclicker.domain.models.*
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import com.example.fantasyquestclicker.domain.use_cases.*
import com.example.fantasyquestclicker.domain.utils.EnemyGenerator
import com.example.fantasyquestclicker.domain.utils.QuestGenerator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BattleViewModel(
    gameRepository: GameRepository
) : BaseGameViewModel(gameRepository) {
    private var stageTimerJob: Job? = null
    private val _currentEnemy = MutableStateFlow(EnemyGenerator.generateEnemy(stage = 1))
    val currentEnemy: StateFlow<Enemy> = _currentEnemy.asStateFlow()

    override fun onPlayerLoaded(player: Player) {
        spawnNewEnemy()
        startStageTimer()
    }

    // Use Cases
    private val attackEnemyUseCase = AttackEnemyUseCase()
    private val stageProgressUseCase = StageProgressUseCase()

    private fun startStageTimer() {
        stageTimerJob?.cancel()
        stageTimerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                decreaseStageTime()
            }
        }
    }

    private fun decreaseStageTime() {
        val currentPlayer = _playerState.value
        if (currentPlayer.currentTime > 0) {
            val updatedPlayer = currentPlayer.copy(
                currentTime = currentPlayer.currentTime - 1
            )
            _playerState.value = updatedPlayer

            if (updatedPlayer.isDefeated) {
                handlePlayerDefeat()
            }
        }
    }

    private fun spawnNewEnemy() {
        val currentPlayer = _playerState.value
        val isBoss = currentPlayer.isBossFight

        val newEnemy = EnemyGenerator.generateEnemy(
            stage = currentPlayer.currentStage,
            isBoss = isBoss
        )

        _currentEnemy.value = newEnemy
    }

    fun attackEnemy() {
        val result = attackEnemyUseCase(_playerState.value, _currentEnemy.value)
        val currentEnemy = _currentEnemy.value

        val updatedPlayer = if (result.isEnemyDefeated) {
            val isKillQuestEnemy = QuestGenerator.getQuest(QuestType.KILL_COUNT)?.targetName == currentEnemy.name

            result.updatedPlayer.copy(
                totalKills = result.updatedPlayer.totalKills + 1,
                totalKillsEnemy = if (isKillQuestEnemy) {
                    result.updatedPlayer.totalKillsEnemy + 1
                } else {
                    result.updatedPlayer.totalKillsEnemy
                }
            )
        } else {
            result.updatedPlayer
        }

        _playerState.value = updatedPlayer
        _currentEnemy.value = result.updatedEnemy

        if (result.isEnemyDefeated) {
            handleEnemyDefeat()
        }

        savePlayerProgress()
    }

    private fun handleEnemyDefeat() {
        val playerAfterProgress = stageProgressUseCase(_playerState.value)
        _playerState.value = playerAfterProgress

        savePlayerProgress()
        spawnNewEnemy()
    }

    private fun handlePlayerDefeat() {
        val resetPlayer = _playerState.value.copy(
            enemiesDefeated = 0,
            currentTime = _playerState.value.maxTime
        )
        _playerState.value = resetPlayer
        spawnNewEnemy()
    }
}