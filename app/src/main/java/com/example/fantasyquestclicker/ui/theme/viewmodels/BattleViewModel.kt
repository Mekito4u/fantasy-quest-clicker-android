package com.example.fantasyquestclicker.ui.theme.viewmodels

import Enemy
import Player
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
<<<<<<< HEAD
import com.example.fantasyquestclicker.domain.models.*
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import com.example.fantasyquestclicker.domain.use_cases.*
import com.example.fantasyquestclicker.domain.utils.EnemyGenerator
=======
import com.example.fantasyquestclicker.data.repositories.PlayerDataStoreRepository
import com.example.fantasyquestclicker.domain.use_cases.AttackEnemyUseCase
>>>>>>> 4a10e87b5862635fd87257a57753d1652998dc1f
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*

<<<<<<< HEAD
class BattleViewModel(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _playerState = MutableStateFlow(Player())
    val player: StateFlow<Player> = _playerState.asStateFlow()

    private val _currentEnemy = MutableStateFlow(EnemyGenerator.generateEnemy(stage = 1))
    val currentEnemy: StateFlow<Enemy> = _currentEnemy.asStateFlow()

    private val _gameState = MutableStateFlow(GameState.PLAYING)
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()
=======
class BattleViewModel(application: Application) : AndroidViewModel(application) {

    // Репозиторий для работы с DataStore
    private val repository = PlayerDataStoreRepository(application.applicationContext)
    private val attackUseCase = AttackEnemyUseCase()
    private val _playerState = MutableStateFlow(Player())


    // Композиция Flow для Player
    val player = combine(
        _playerState,
        repository.getGold(),
        repository.getLevel(),
        repository.getExperience(),
        repository.getAttack()
    ) { local, gold, level, exp, attack ->
        Player(
            gold = gold,
            level = level,
            experience = exp,
            baseAttack = attack
        ).also { _playerState.value = it }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Player())

    // Временный противник
    private val _currentEnemy = MutableStateFlow(createGoblin())
    val currentEnemy: StateFlow<Enemy> = _currentEnemy.asStateFlow()
>>>>>>> 4a10e87b5862635fd87257a57753d1652998dc1f

    private var enemyTimer: Timer? = null
    private var stageTimer: Timer? = null

    // Use Cases
    private val attackEnemyUseCase = AttackEnemyUseCase()
    private val enemyAttackUseCase = EnemyAttackUseCase()
    private val stageProgressUseCase = StageProgressUseCase()
    private val handleDefeatUseCase = HandleDefeatUseCase()
    private val decreaseStageTimeUseCase = DecreaseStageTimeUseCase()
    private val addTimeRewardUseCase = AddTimeRewardUseCase()

    init {
        loadPlayerProgress()
        startEnemyAttackTimer()
        startStageTimer()
    }

    private fun loadPlayerProgress() {
        viewModelScope.launch {
<<<<<<< HEAD
            val savedPlayer = gameRepository.loadPlayerProgress()
            _playerState.value = savedPlayer
            spawnNewEnemy()
        }
    }
=======
            val playerValue = player.value.copy()
            val enemyValue = _currentEnemy.value.copy()
>>>>>>> 4a10e87b5862635fd87257a57753d1652998dc1f

    private fun savePlayerProgress() {
        viewModelScope.launch {
            gameRepository.savePlayerProgress(_playerState.value)
        }
    }

<<<<<<< HEAD
    private fun startStageTimer() {
        stageTimer?.cancel()

        stageTimer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    decreaseStageTime()
                }
            }, 1000, 1000)
        }
    }

    private fun decreaseStageTime() {
        viewModelScope.launch {
            if (_gameState.value == GameState.PLAYING) {
                val updatedPlayer = decreaseStageTimeUseCase(_playerState.value)
                _playerState.value = updatedPlayer

                if (updatedPlayer.isDefeated) {
                    handlePlayerDefeat()
                }
=======
            _playerState.value = playerValue
            _currentEnemy.value = enemyValue

            if (result.isEnemyDefeated) {
                // Сохраняем изменения в DataStore
                repository.savePlayer(
                    gold = playerValue.gold,
                    level = playerValue.level,
                    experience = playerValue.experience,
                    attack = playerValue.baseAttack
                )
                spawnNewEnemy()
>>>>>>> 4a10e87b5862635fd87257a57753d1652998dc1f
            }
        }
    }

<<<<<<< HEAD
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
=======
    private fun spawnNewEnemy() {
        val randomEnemy = when ((1..3).random()) {
            1 -> createGoblin()
            2 -> createOrc()
            3 -> createSkeleton()
            else -> createGoblin()
        }
        _currentEnemy.value = randomEnemy
    }

    private fun createGoblin(): Enemy {
        return Enemy(
            id = 1,
            name = "Гоблин",
            currentHealth = 50,
            maxHealth = 50,
            baseReward = 25,
            imageRes = "goblin"
        )
    }

    private fun createOrc(): Enemy {
        return Enemy(
            id = 2,
            name = "Орк",
            currentHealth = 80,
            maxHealth = 80,
            baseReward = 40,
            imageRes = "orc"
>>>>>>> 4a10e87b5862635fd87257a57753d1652998dc1f
        )

        _currentEnemy.value = newEnemy
        startEnemyAttackTimer()
    }

    private fun startEnemyAttackTimer() {
        enemyTimer?.cancel()
        val enemy = _currentEnemy.value

        enemyTimer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    enemyAttackPlayer()
                }
            }, enemy.attackInterval, enemy.attackInterval)
        }
    }

    private fun enemyAttackPlayer() {
        viewModelScope.launch {
            val updatedPlayer = enemyAttackUseCase(_playerState.value, _currentEnemy.value)
            _playerState.value = updatedPlayer

            if (updatedPlayer.isDefeated) {
                handlePlayerDefeat()
            }
        }
    }

    private fun handlePlayerDefeat() {
        enemyTimer?.cancel()
        stageTimer?.cancel()
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

    override fun onCleared() {
        super.onCleared()
        enemyTimer?.cancel()
        stageTimer?.cancel()
    }

    private fun createSkeleton(): Enemy {
        return Enemy(
            id = 3,
            name = "Скелет",
            currentHealth = 60,
            maxHealth = 60,
            baseReward = 30,
            imageRes = "skeleton"
        )
    }
}