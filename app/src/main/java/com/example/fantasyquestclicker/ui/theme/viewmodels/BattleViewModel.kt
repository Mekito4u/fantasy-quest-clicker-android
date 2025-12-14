package com.example.fantasyquestclicker.ui.theme.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.models.QuestType
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import com.example.fantasyquestclicker.domain.use_cases.AttackEnemyUseCase
import com.example.fantasyquestclicker.domain.use_cases.StageProgressUseCase
import com.example.fantasyquestclicker.domain.utils.EnemyGenerator
import com.example.fantasyquestclicker.domain.utils.QuestGenerator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * ViewModel, управляющий состоянием и логикой экрана боя.
 * Обрабатывает взаимодействие игрока: атаки, таймер уровня, смену врагов.
 * @param gameRepository Репозиторий для доступа к игровым данным.
 */
class BattleViewModel(
    gameRepository: GameRepository
) : BaseGameViewModel(gameRepository) {
    private var stageTimerJob: Job? = null
    private val _currentEnemy = MutableStateFlow(EnemyGenerator.generateEnemy(stage = 1))

    /**
     * Поток состояния текущего врага на экране. Подписывайся на него для обновления UI.
     */
    val currentEnemy: StateFlow<Enemy> = _currentEnemy.asStateFlow()

    /**
     * Вызывается при успешной загрузке данных игрока. Инициализирует уровень.
     * @param player Загруженный объект игрока.
     */
    override fun onPlayerLoaded(player: Player) {
        spawnNewEnemy()
        startStageTimer()
    }

    // Use Cases
    private val attackEnemyUseCase = AttackEnemyUseCase()
    private val stageProgressUseCase = StageProgressUseCase()

    /**
     * Запускает таймер, который каждую секунду уменьшает время на уровне.
     * Таймер автоматически отменяется при очистке ViewModel.
     */
    private fun startStageTimer() {
        stageTimerJob?.cancel()
        stageTimerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                decreaseStageTime()
            }
        }
    }

    /**
     * Уменьшает текущее время игрока на 1 и проверяет, не проиграл ли он.
     */
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

    /**
     * Создаёт нового врага в соответствии с текущим этапом и прогрессом игрока.
     */
    private fun spawnNewEnemy() {
        val currentPlayer = _playerState.value
        val isBoss = currentPlayer.isBossFight

        val newEnemy = EnemyGenerator.generateEnemy(
            stage = currentPlayer.currentStage,
            isBoss = isBoss
        )

        _currentEnemy.value = newEnemy
    }

    /**
     * Основной метод для обработки атаки игрока. Вызывается из UI по нажатию.
     * Выполняет Use Case атаки, обновляет состояния игрока и врага, обрабатывает последствия.
     */
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

    /**
     * Обрабатывает логику после победы над врагом: обновляет прогресс уровня и спавнит нового врага.
     */
    private fun handleEnemyDefeat() {
        val playerAfterProgress = stageProgressUseCase(_playerState.value)
        _playerState.value = playerAfterProgress

        savePlayerProgress()
        spawnNewEnemy()
    }

    /**
     * Сбрасывает прогресс уровня при поражении игрока (закончилось время).
     */
    private fun handlePlayerDefeat() {
        val resetPlayer = _playerState.value.copy(
            enemiesDefeated = 0,
            currentTime = _playerState.value.maxTime
        )
        _playerState.value = resetPlayer
        spawnNewEnemy()
    }
}