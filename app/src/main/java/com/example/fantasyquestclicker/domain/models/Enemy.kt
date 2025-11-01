data class Enemy(
    val id: Int, // ID
    val name: String, // Имя
    var currentHealth: Int, // Текущее здоровье
    var maxHealth: Int, // Максимальное здоровье
    val baseReward: Int, // Базовая награда
    val imageRes: String, // Иконка
)