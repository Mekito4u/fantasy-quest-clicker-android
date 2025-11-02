package com.example.fantasyquestclicker.ui.theme.viewmodels

import Enemy
import Player
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fantasyquestclicker.data.repositories.PlayerDataStoreRepository
import com.example.fantasyquestclicker.domain.use_cases.AttackEnemyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    fun attackEnemy() {
        viewModelScope.launch {
            val playerValue = player.value.copy()
            val enemyValue = _currentEnemy.value.copy()

            val result = attackUseCase.execute(playerValue, enemyValue)

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
            }
        }
    }

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
        )
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