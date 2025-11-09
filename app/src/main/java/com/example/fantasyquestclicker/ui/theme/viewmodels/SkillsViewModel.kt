package com.example.fantasyquestclicker.ui.theme.viewmodels

import com.example.fantasyquestclicker.domain.models.SkillType
import com.example.fantasyquestclicker.domain.repositories.GameRepository

class SkillsViewModel(
    gameRepository: GameRepository
) : BaseGameViewModel(gameRepository) {

    fun upgradeSkill(skillType: SkillType) {
        val currentPlayer = _playerState.value
        val cost = skillType.getUpgradeCost(currentPlayer)

        val updatedPlayer = currentPlayer.copy(
            gold = currentPlayer.gold - cost,
            upgradeSkills = currentPlayer.upgradeSkills + 1
        ).let { basePlayer ->
            when (skillType) {
                SkillType.ATTACK -> basePlayer.copy(baseAttack = basePlayer.baseAttack + 5)
                SkillType.TIME -> basePlayer.copy(maxTime = basePlayer.maxTime + 5)
                SkillType.CRITICAL -> basePlayer.copy(
                    criticalChance = (basePlayer.criticalChance + 0.01).coerceAtMost(0.5)
                )
            }
        }

        _playerState.value = updatedPlayer
        savePlayerProgress()
    }
}