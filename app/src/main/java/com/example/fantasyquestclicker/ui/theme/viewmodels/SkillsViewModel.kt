package com.example.fantasyquestclicker.ui.theme.viewmodels

import com.example.fantasyquestclicker.domain.models.SkillType
import com.example.fantasyquestclicker.domain.repositories.GameRepository

class SkillsViewModel(
    gameRepository: GameRepository
) : BaseGameViewModel(gameRepository) {

    fun upgradeSkill(skillType: SkillType) {
        val cost = skillType.getUpgradeCost(_playerState.value)
        val currentPlayer = _playerState.value

        if (currentPlayer.gold >= cost) {
            val updatedPlayer = when (skillType) {
                SkillType.ATTACK -> currentPlayer.copy(
                    gold = currentPlayer.gold - cost,
                    baseAttack = currentPlayer.baseAttack + 5
                )
                SkillType.TIME -> currentPlayer.copy(
                    gold = currentPlayer.gold - cost,
                    maxTime = currentPlayer.maxTime + 5
                )
                SkillType.CRITICAL -> currentPlayer.copy(
                    gold = currentPlayer.gold - cost,
                    criticalChance = (currentPlayer.criticalChance + 0.01).coerceAtMost(0.5)
                )
            }
            _playerState.value = updatedPlayer
            savePlayerProgress()
        }
    }
}