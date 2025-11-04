package com.example.fantasyquestclicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fantasyquestclicker.ui.theme.screens.BattleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BattleScreen(
                onNavigateToSkills = {
                },
                onNavigateToQuests = {
                },
                onBackClick = {
                    finish()
                }
            )
        }
    }
}