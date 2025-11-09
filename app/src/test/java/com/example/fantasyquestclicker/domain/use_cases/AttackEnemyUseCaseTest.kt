package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.models.Player
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AttackEnemyUseCaseTest {
    // Создание тестового противника
    private fun createTestEnemy(
        currentHealth: Int = 50,
        maxHealth: Int = 50,
        baseReward: Int = 25
    ): Enemy {
        return Enemy(
            currentHealth = currentHealth,
            maxHealth = maxHealth,
            id = 1,
            name = "Test Enemy",
            baseReward = baseReward,
            imageRes = ""
        )
    }

    // Тест для атаки противника
    @Test
    fun `attack enemy reduces health`() {
        // Given - подготовка данных
        val useCase = AttackEnemyUseCase()
        val player = Player(baseAttack = 10)
        val enemy = createTestEnemy(currentHealth = 50, maxHealth = 50)

        // When - выполнение действия
        val result = useCase(player, enemy)

        // Then - проверка результатов
        assertTrue("Враг должен получать урон", result.updatedEnemy.currentHealth < enemy.currentHealth)
        assertEquals("Здоровье должно уменьшаться на атаку игрока", 40, result.updatedEnemy.currentHealth)
    }

    // Тест для критической атаки
    @Test
    fun `critical attack deals double damage`() {
        val useCase = AttackEnemyUseCase()
        val player = Player(baseAttack = 10, criticalChance = 1.0) // 100% шанс критического удара
        val enemy = createTestEnemy(currentHealth = 50, maxHealth = 50)

        val result = useCase(player, enemy)

        assertTrue("Должен нанести критический урон", result.damageDealt > player.baseAttack)
        assertEquals("Урон должен быть удвоен", 30, result.updatedEnemy.currentHealth)
    }

    // Тест что здоровье не может быть ниже нуля
    @Test
    fun `enemy health never goes below zero`() {
        val useCase = AttackEnemyUseCase()
        val player = Player(baseAttack = 100) // Большой урон
        val enemy = createTestEnemy(currentHealth = 10, maxHealth = 50) // Мало здоровья

        val result = useCase(player, enemy)

        assertEquals("Здоровье не должно быть ниже нуля", 0, result.updatedEnemy.currentHealth)
    }

    // Тест на награду за убийство
    @Test
    fun `gold reward when enemy defeated`() {
        val useCase = AttackEnemyUseCase()
        val player = Player(baseAttack = 60)
        val enemy = createTestEnemy(currentHealth = 50, maxHealth = 50, baseReward = 25)

        val result = useCase(player, enemy)

        assertTrue("Враг должен быть убит", result.isEnemyDefeated)
        assertEquals("Игрок должен получить награду за убийство", 25, result.goldReward)
    }
}