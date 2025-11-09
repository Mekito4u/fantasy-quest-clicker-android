package com.example.fantasyquestclicker.data.repositories

import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.repositories.GameRepository as DomainGameRepository
import com.example.fantasyquestclicker.domain.utils.QuestGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GameRepository(
    private val playerRepository: PlayerRepository,
    private val questRepository: QuestRepository
) : DomainGameRepository {

    override suspend fun savePlayerProgress(player: Player) {
        playerRepository.savePlayer(player)
        questRepository.saveQuests(QuestGenerator.getQuests(player))
    }

    override suspend fun loadPlayerProgress(): Player {
        val player = playerRepository.loadPlayer()
        val savedQuests = questRepository.loadQuests()
        if (savedQuests.isNotEmpty()) {
            QuestGenerator.restoreQuests(savedQuests)
        }
        return player
    }

    override fun getPlayerProgressStream(): Flow<Player> {
        return flow {
            val player = loadPlayerProgress()
            emit(player)
        }
    }
}