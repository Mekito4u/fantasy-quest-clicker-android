package com.example.fantasyquestclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.fantasyquestclicker.ui.theme.screens.BattleScreen
import com.example.fantasyquestclicker.ui.theme.screens.QuestsScreen
import com.example.fantasyquestclicker.ui.theme.screens.SkillsScreen

/**
 * Главная активность приложения, служащая точкой входа и контейнером для навигации между экранами.
 *
 * Эта активность отвечает за:
 * - Настройку внешнего вида системных панелей (статусной и навигационной)
 * - Управление текущим отображаемым экраном с помощью Compose
 * - Обеспечение навигации между тремя основными экранами приложения
 *
 * @constructor Создает экземпляр главной активности приложения.
 *
 * @see ComponentActivity
 *
 * @property window Используется для изменения цвета системных панелей
 * @property setContent Задает Compose-контент для активности
 *
 * @sample com.example.fantasyquestclicker.MainActivity.onCreate
 *
 * @note Для поддержки прозрачных панелей на Android используются устаревшие методы
 * @see Color.Transparent
 */
@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {

    /**
     * Вызывается при создании активности. Инициализирует UI и настройки окна.
     *
     * Основные действия:
     * 1. Настраивает прозрачный цвет для навигационной и статусной панелей
     * 2. Устанавливает Compose-контент с навигацией между экранами
     * 3. Инициализирует состояние текущего экрана
     *
     * @param savedInstanceState Сохраненное состояние активности, если оно есть
     *
     * @receiver Экземпляр MainActivity
     *
     * @throws SecurityException Если нет разрешения на изменение системных UI параметров
     *
     * @sample com.example.fantasyquestclicker.MainActivityTest.testOnCreate
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Убираем белый цвет навигационной панели
        window.navigationBarColor = Color.Transparent.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()

        // Устанавливаем контент главного экрана
        setContent {
            var currentScreen by remember { mutableStateOf("battle") }

            // Отрисовываем текущий экран
            when (currentScreen) {
                "battle" -> BattleScreen(
                    currentScreen = currentScreen,
                    onScreenChange = { newScreen -> currentScreen = newScreen },
                )
                "skills" -> SkillsScreen(
                    currentScreen = currentScreen,
                    onScreenChange = { newScreen -> currentScreen = newScreen },
                )
                "quests" -> QuestsScreen(
                    currentScreen = currentScreen,
                    onScreenChange = { newScreen -> currentScreen = newScreen },
                )
            }
        }
    }
}