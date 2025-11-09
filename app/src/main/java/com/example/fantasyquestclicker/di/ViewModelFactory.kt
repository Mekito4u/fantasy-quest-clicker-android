package com.example.fantasyquestclicker.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fantasyquestclicker.data.repositories.GameRepository
import com.example.fantasyquestclicker.data.repositories.PlayerRepository
import com.example.fantasyquestclicker.data.repositories.QuestRepository
import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel
import com.example.fantasyquestclicker.ui.theme.viewmodels.QuestsViewModel
import com.example.fantasyquestclicker.ui.theme.viewmodels.SkillsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val playerRepository = PlayerRepository(context)
        val questRepository = QuestRepository(context)
        val gameRepository = GameRepository(playerRepository, questRepository)

        return when {
            modelClass.isAssignableFrom(BattleViewModel::class.java) -> {
                BattleViewModel(gameRepository) as T
            }
            modelClass.isAssignableFrom(SkillsViewModel::class.java) -> {
                SkillsViewModel(gameRepository) as T
            }
            modelClass.isAssignableFrom(QuestsViewModel::class.java) -> {
                QuestsViewModel(gameRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}