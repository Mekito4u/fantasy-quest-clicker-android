package com.example.fantasyquestclicker.domain.repositories

import com.example.fantasyquestclicker.domain.models.Player
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun savePlayerProgress(player: Player)
    suspend fun loadPlayerProgress(): Player
    fun getPlayerProgressStream(): Flow<Player>
}