package com.example.fantasyquestclicker.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.fantasyquestclicker.ui.theme.viewmodels.BattleViewModel

@Composable
fun BattleScreen() {
    val viewModel: BattleViewModel = viewModel()

    val player = viewModel.player.collectAsState().value
    val enemy = viewModel.currentEnemy.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text("Уровень: ${player.level}")
        Text("Золото: ${player.gold}")

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.attackEnemy() }
        ) {
            Text("АТАКОВАТЬ!") }
    }
}
