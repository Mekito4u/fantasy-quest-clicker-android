data class Player(
    val id: Int = 1, // ID
    var level: Int = 1, // Уровень
    var experience: Int = 0, // Опыт
    var gold: Int = 0, // Золото
    var baseAttack: Int = 10, // Базовая атака
    var currentHealth: Int = 100, // Текущее здоровье
    var maxHealth: Int = 100, // Максимальное здоровье
)