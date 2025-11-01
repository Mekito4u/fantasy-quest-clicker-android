package com.example.fantasyquestclicker.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import Player
import Enemy
import AttackEnemyUseCase

class BattleViewModel : ViewModel() {

    private val _player = MutableStateFlow(Player())
    val player: StateFlow<Player> = _player

    private val _currentEnemy = MutableStateFlow(
        Enemy(
            id = 1,
            name = "Goblin",
            currentHealth = 50,
            maxHealth = 50,
            baseReward = 25,
            imageRes = "goblin",
        )
    )
    val currentEnemy: StateFlow<Enemy> = _currentEnemy

    private val attackUseCase = AttackEnemyUseCase()

    fun attackEnemy() {
        viewModelScope.launch {
            val playerValue = _player.value
            val enemyValue = _currentEnemy.value

            val result = attackUseCase.execute(playerValue, enemyValue)

            _player.value = playerValue
            _currentEnemy.value = enemyValue

            if (result.isEnemyDefeated) {
                spawnNewEnemy()
            }
        }
    }

    fun spawnNewEnemy() {
        _currentEnemy.value = Enemy(
            id = 2,
            name = "Orc",
            currentHealth = 80,
            maxHealth = 80,
            baseReward = 40,
            imageRes = "orc"
        )
    }
}