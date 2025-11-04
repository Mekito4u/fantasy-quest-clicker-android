package com.example.fantasyquestclicker.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fantasyquestclicker.domain.models.Player
import com.example.fantasyquestclicker.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "player_progress")

class PlayerProgressRepository(
    private val context: Context
) : GameRepository {

    companion object {
        private val GOLD_KEY = intPreferencesKey("gold")
        private val BASE_ATTACK_KEY = intPreferencesKey("base_attack")
        private val MAX_TIME_KEY = intPreferencesKey("max_time")
        private val ARMOR_KEY = intPreferencesKey("armor")
        private val CRITICAL_CHANCE_KEY = intPreferencesKey("critical_chance")
    }

    override suspend fun savePlayerProgress(player: Player) {
        context.dataStore.edit { preferences ->
            preferences[GOLD_KEY] = player.gold
            preferences[BASE_ATTACK_KEY] = player.baseAttack
            preferences[MAX_TIME_KEY] = player.maxTime
            preferences[ARMOR_KEY] = player.armor
        }
    }

    override suspend fun loadPlayerProgress(): Player {
        return Player(
            gold = context.dataStore.data.map { it[GOLD_KEY] ?: 0 }.first(),
            baseAttack = context.dataStore.data.map { it[BASE_ATTACK_KEY] ?: 10 }.first(),
            maxTime = context.dataStore.data.map { it[MAX_TIME_KEY] ?: 60 }.first(),
            armor = context.dataStore.data.map { it[ARMOR_KEY] ?: 0 }.first(),
            criticalChance = 0.1
        )
    }

    override fun getPlayerProgressStream(): Flow<Player> {
        return context.dataStore.data.map { preferences ->
            Player(
                gold = preferences[GOLD_KEY] ?: 0,
                baseAttack = preferences[BASE_ATTACK_KEY] ?: 10,
                maxTime = preferences[MAX_TIME_KEY] ?: 60,
                armor = preferences[ARMOR_KEY] ?: 0,
                criticalChance = 0.1
            )
        }
    }
}