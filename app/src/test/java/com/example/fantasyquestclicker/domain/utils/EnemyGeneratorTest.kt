package com.example.fantasyquestclicker.domain.utils

import org.junit.Assert.assertTrue
import org.junit.Test

class EnemyGeneratorTest {

    @Test
    fun `generate normal enemy for stage`() {
        val enemy = EnemyGenerator.generateEnemy(stage = 1)

        assertTrue("Враг должен иметь здоровье", enemy.maxHealth > 0)
        assertTrue("Враг должен иметь награду", enemy.baseReward > 0)
    }

    @Test
    fun `boss has increased stats`() {
        val normal = EnemyGenerator.generateEnemy(stage = 3)
        val boss = EnemyGenerator.generateEnemy(stage = 3, isBoss = true)

        assertTrue("Босс должен иметь больше здоровья", boss.maxHealth > normal.maxHealth)
        assertTrue("Босс должен иметь имя", boss.name.contains("Босс"))
    }
}