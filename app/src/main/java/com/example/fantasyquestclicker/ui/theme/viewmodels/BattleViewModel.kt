package com.example.fantasyquestclicker.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fantasyquestclicker.domain.models.*
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import com.example.fantasyquestclicker.domain.use_cases.*
import com.example.fantasyquestclicker.domain.utils.EnemyGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class BattleViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _playerState = MutableStateFlow(Player())
    val player: StateFlow<Player> = _playerState.asStateFlow()

    private val _currentEnemy = MutableStateFlow(EnemyGenerator.generateEnemy(stage = 1))
    val currentEnemy: StateFlow<Enemy> = _currentEnemy.asStateFlow()

    private val _gameState = MutableStateFlow(GameState.PLAYING)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()


    // Use Cases
    private val attackEnemyUseCase = AttackEnemyUseCase()
    private val stageProgressUseCase = StageProgressUseCase()
    private val handleDefeatUseCase = HandleDefeatUseCase()
    private val decreaseStageTimeUseCase = DecreaseStageTimeUseCase()
    private val addTimeRewardUseCase = AddTimeRewardUseCase()

    init {
        loadPlayerProgress()
        startStageTimer()
    }

    private fun loadPlayerProgress() {
        viewModelScope.launch {
            val savedPlayer = gameRepository.loadPlayerProgress()
            _playerState.value = savedPlayer
            spawnNewEnemy()
        }
    }

    private fun savePlayerProgress() {
        viewModelScope.launch {
            gameRepository.savePlayerProgress(_playerState.value)
        }
    }

    private fun startStageTimer() {
        viewModelScope.launch {
            while (isActive && _gameState.value == GameState.PLAYING) {
                decreaseStageTime()
                delay(1000)
            }
        }
    }

    private fun decreaseStageTime() {
        val updatedPlayer = decreaseStageTimeUseCase(_playerState.value)
        _playerState.value = updatedPlayer

        if (updatedPlayer.isDefeated) {
            handlePlayerDefeat()
        }
    }

    fun attackEnemy() {
        val result = attackEnemyUseCase(_playerState.value, _currentEnemy.value)

        _playerState.value = result.updatedPlayer
        _currentEnemy.value = result.updatedEnemy

        if (result.isEnemyDefeated) {
            handleEnemyDefeat(result)
        }

        savePlayerProgress()
    }

    private fun handleEnemyDefeat(result: AttackResult) {
        val playerAfterProgress = stageProgressUseCase(_playerState.value)
        val isBoss = playerAfterProgress.isBossFight

        val playerWithTimeReward = addTimeRewardUseCase(playerAfterProgress, isBoss)

        _playerState.value = playerWithTimeReward
        savePlayerProgress()
        spawnNewEnemy()
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


    private fun handlePlayerDefeat() {
        _gameState.value = GameState.DEFEAT

        val resetPlayer = handleDefeatUseCase(_playerState.value)
        _playerState.value = resetPlayer
        savePlayerProgress()

        viewModelScope.launch {
            kotlinx.coroutines.delay(2000)
            _gameState.value = GameState.PLAYING
            startStageTimer()
            spawnNewEnemy()
        }
    }
}