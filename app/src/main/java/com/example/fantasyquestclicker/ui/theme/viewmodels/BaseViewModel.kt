package com.example.fantasyquestclicker.ui.theme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseGameViewModel(
    protected val gameRepository: GameRepository
) : ViewModel() {
    protected val _playerState = MutableStateFlow(Player())
    val player: StateFlow<Player> = _playerState.asStateFlow()

    fun loadPlayerProgress() {
        viewModelScope.launch {
            val savedPlayer = gameRepository.loadPlayerProgress()
            _playerState.value = savedPlayer
            onPlayerLoaded(savedPlayer)
        }
    }

    fun savePlayerProgress() {
        viewModelScope.launch {
            gameRepository.savePlayerProgress(_playerState.value)
        }
    }

    protected open fun onPlayerLoaded(player: Player) {
    }
}