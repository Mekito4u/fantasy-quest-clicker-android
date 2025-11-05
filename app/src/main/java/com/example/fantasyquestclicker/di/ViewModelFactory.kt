package com.example.fantasyquestclicker.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fantasyquestclicker.data.repositories.PlayerProgressRepository
import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel
import com.example.fantasyquestclicker.ui.theme.viewmodels.SkillsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = PlayerProgressRepository(context)

        return when {
            modelClass.isAssignableFrom(BattleViewModel::class.java) -> {
                BattleViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SkillsViewModel::class.java) -> {
                SkillsViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}