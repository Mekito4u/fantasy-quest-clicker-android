package com.example.fantasyquestclicker.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.fantasyquestclicker.domain.models.Player
import kotlinx.coroutines.flow.first

private val Context.playerDataStore: DataStore<Preferences> by preferencesDataStore(name = "player_data")

class PlayerRepository(private val context: Context) {

    companion object {
        private val GOLD_KEY = intPreferencesKey("gold")
        private val BASE_ATTACK_KEY = intPreferencesKey("base_attack")
        private val MAX_TIME_KEY = intPreferencesKey("max_time")
        private val CRITICAL_CHANCE_KEY = floatPreferencesKey("critical_chance")
        private val CURRENT_STAGE_KEY = intPreferencesKey("current_stage")
        private val TOTAL_KILLS_KEY = intPreferencesKey("total_kills")
        private val TOTAL_KILLS_ENEMY_KEY = intPreferencesKey("total_kills_enemy")
        private val UPGRADE_SKILLS_KEY = intPreferencesKey("upgrade_skills")
        private val TOTAL_GOLD_EARNED_KEY = intPreferencesKey("total_gold_earned")
    }

    suspend fun savePlayer(player: Player) {
        context.playerDataStore.edit { preferences ->
            preferences[GOLD_KEY] = player.gold
            preferences[BASE_ATTACK_KEY] = player.baseAttack
            preferences[MAX_TIME_KEY] = player.maxTime
            preferences[CRITICAL_CHANCE_KEY] = player.criticalChance.toFloat()
            preferences[CURRENT_STAGE_KEY] = player.currentStage
            preferences[TOTAL_KILLS_KEY] = player.totalKills
            preferences[TOTAL_KILLS_ENEMY_KEY] = player.totalKillsEnemy
            preferences[UPGRADE_SKILLS_KEY] = player.upgradeSkills
            preferences[TOTAL_GOLD_EARNED_KEY] = player.totalGoldEarned
        }
    }

    suspend fun loadPlayer(): Player {
        val preferences = context.playerDataStore.data.first()
        return Player(
            gold = preferences[GOLD_KEY] ?: 0,
            baseAttack = preferences[BASE_ATTACK_KEY] ?: 5,
            maxTime = preferences[MAX_TIME_KEY] ?: 10,
            criticalChance = preferences[CRITICAL_CHANCE_KEY]?.toDouble() ?: 0.0,
            currentStage = preferences[CURRENT_STAGE_KEY] ?: 1,
            totalKills = preferences[TOTAL_KILLS_KEY] ?: 0,
            totalKillsEnemy = preferences[TOTAL_KILLS_ENEMY_KEY] ?: 0,
            upgradeSkills = preferences[UPGRADE_SKILLS_KEY] ?: 0,
            totalGoldEarned = preferences[TOTAL_GOLD_EARNED_KEY] ?: 0
        )
    }
}