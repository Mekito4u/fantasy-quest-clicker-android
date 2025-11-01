class AttackEnemyUseCase {
    fun execute(player: Player, enemy: Enemy): AttackResult {
        val damage = player.baseAttack
        enemy.currentHealth -= damage
        val isEnemyDefeated = enemy.currentHealth <= 0

        var goldReward = 0
        if (isEnemyDefeated) {
            goldReward = enemy.baseReward
            player.gold += goldReward
        }

        return AttackResult(
            damageDealt = damage,
            isEnemyDefeated = isEnemyDefeated,
            goldReward = goldReward)
    }
}

data class AttackResult(
    val damageDealt: Int,
    val isEnemyDefeated: Boolean,
    val goldReward: Int
)