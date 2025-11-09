package com.example.fantasyquestclicker.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.fantasyquestclicker.domain.models.Quest
import com.example.fantasyquestclicker.domain.models.QuestType
import kotlinx.coroutines.flow.first

private val Context.questDataStore: DataStore<Preferences> by preferencesDataStore(name = "quest_data")

// Репозиторий для работы с квестами
class QuestRepository(private val context: Context) {
    // Сохранение квестов
    suspend fun saveQuests(quests: List<Quest>) {
        context.questDataStore.edit { preferences ->
            quests.forEachIndexed { index, quest ->
                preferences[intPreferencesKey("quest_${index}_type")] = quest.type.ordinal
                preferences[intPreferencesKey("quest_${index}_target")] = quest.targetValue
                preferences[intPreferencesKey("quest_${index}_reward")] = quest.reward
                preferences[stringPreferencesKey("quest_${index}_name")] = quest.targetName
            }
            preferences[intPreferencesKey("quests_count")] = quests.size
        }
    }

    // Загрузка квестов
    suspend fun loadQuests(): List<Quest> {
        val preferences = context.questDataStore.data.first()
        val count = preferences[intPreferencesKey("quests_count")] ?: 0

        return (0 until count).mapNotNull { index ->
            val typeOrdinal = preferences[intPreferencesKey("quest_${index}_type")] ?: return@mapNotNull null
            val type = QuestType.entries.getOrNull(typeOrdinal) ?: return@mapNotNull null

            Quest(
                type = type,
                targetValue = preferences[intPreferencesKey("quest_${index}_target")] ?: 0,
                reward = preferences[intPreferencesKey("quest_${index}_reward")] ?: 0,
                targetName = preferences[stringPreferencesKey("quest_${index}_name")] ?: ""
            )
        }
    }
}