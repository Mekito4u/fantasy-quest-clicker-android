// data/repositories/PlayerDataStoreRepository.kt
package com.example.fantasyquestclicker.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Расширение для создания DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "game_settings")

class PlayerDataStoreRepository(private val context: Context) {

    companion object {
        private val GOLD_KEY = intPreferencesKey("gold")
        private val LEVEL_KEY = intPreferencesKey("level")
        private val EXPERIENCE_KEY = intPreferencesKey("experience")
        private val ATTACK_KEY = intPreferencesKey("attack")
    }

    // Сохранение данных
    suspend fun savePlayer(gold: Int, level: Int, experience: Int, attack: Int) {
        context.dataStore.edit { preferences ->
            preferences[GOLD_KEY] = gold
            preferences[LEVEL_KEY] = level
            preferences[EXPERIENCE_KEY] = experience
            preferences[ATTACK_KEY] = attack
        }
    }

    // Получить золото
    fun getGold(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[GOLD_KEY] ?: 0
        }

    // Получить уровень
    fun getLevel(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[LEVEL_KEY] ?: 1
        }

    // Получить опыт
    fun getExperience(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[EXPERIENCE_KEY] ?: 0
        }

    // Получить атаку
    fun getAttack(): Flow<Int> = context.dataStore.data
        .map { preferences ->
            preferences[ATTACK_KEY] ?: 10
        }
}