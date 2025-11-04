package com.example.fantasyquestclicker.domain.use_cases

import com.example.fantasyquestclicker.domain.models.Enemy
import com.example.fantasyquestclicker.domain.models.Player
import org.junit.Assert.*
import org.junit.Test

class AttackEnemyUseCaseTest {

    // Тест на обычную атаку
    @Test
    fun `execute should reduce enemy health without defeating when health remains`() {
        // Предусловия
        val player = Player(baseAttack = 10, gold = 0)
        val enemy = Enemy(
            id = 1,
            name = "Гоблин",
            currentHealth = 50,
            maxHealth = 50,
            baseReward = 25,
            imageRes = "goblin"
        )
        val useCase = AttackEnemyUseCase()

        // Когда
        val result = useCase.execute(player, enemy)

        // Тогда
        assertEquals(10, result.damageDealt)
        assertFalse(result.isEnemyDefeated)
        assertEquals(0, result.goldReward)

        // Проверяем, что исходные объекты не изменились
        assertEquals(50, enemy.currentHealth)
        assertEquals(0, player.gold)

        // Проверяем, что возвращены новые объекты
        assertEquals(40, result.updatedEnemy.currentHealth)
        assertEquals(0, result.updatedPlayer.gold)
    }


    // Тест на победу
    @Test
    fun `execute should defeat enemy and give gold when health reaches zero`() {
        // Предусловия
        val player = Player(baseAttack = 50, gold = 0)
        val enemy = Enemy(
            id = 1,
            name = "Гоблин",
            currentHealth = 50,
            maxHealth = 50,
            baseReward = 25,
            imageRes = "goblin"
        )
        val useCase = AttackEnemyUseCase()

        // Когда
        val result = useCase.execute(player, enemy)

        // Тогда
        assertEquals(50, result.damageDealt)
        assertTrue(result.isEnemyDefeated)
        assertEquals(25, result.goldReward)

        // Исходные объекты не изменились
        assertEquals(50, enemy.currentHealth)
        assertEquals(0, player.gold)

        // Новые объекты имеют правильные значения
        assertEquals(0, result.updatedEnemy.currentHealth)
        assertEquals(25, result.updatedPlayer.gold)
    }

    // Тест на то, что золото не добавляется, если враг не побежден
    @Test
    fun `execute should not give gold when enemy not defeated`() {
        // Предусловия
        val player = Player(baseAttack = 30, gold = 100)
        val enemy = Enemy(
            id = 2,
            name = "Орк",
            currentHealth = 80,
            maxHealth = 80,
            baseReward = 40,
            imageRes = "orc"
        )
        val useCase = AttackEnemyUseCase()

        // Когда
        val result = useCase.execute(player, enemy)

        // Тогда
        assertFalse(result.isEnemyDefeated)
        assertEquals(0, result.goldReward)
        assertEquals(100, result.updatedPlayer.gold) // Золото не изменилось
        assertEquals(50, result.updatedEnemy.currentHealth) // Здоровье уменьшилось
    }
}