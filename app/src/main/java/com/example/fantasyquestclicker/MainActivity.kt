package com.example.fantasyquestclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.fantasyquestclicker.ui.theme.screens.BattleScreen
import com.example.fantasyquestclicker.ui.theme.screens.QuestsScreen
import com.example.fantasyquestclicker.ui.theme.screens.SkillsScreen

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.navigationBarColor = Color.Transparent.toArgb()
        window.statusBarColor = Color.Transparent.toArgb()

        setContent {
            var currentScreen by remember { mutableStateOf("battle") }

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