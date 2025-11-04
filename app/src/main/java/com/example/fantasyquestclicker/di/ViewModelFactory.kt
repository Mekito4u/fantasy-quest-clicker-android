package com.example.fantasyquestclicker.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fantasyquestclicker.data.repositories.PlayerProgressRepository
import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BattleViewModel::class.java)) {
            val repository = PlayerProgressRepository(context)
            return BattleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}