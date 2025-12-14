package com.example.fantasyquestclicker.domain.models

/**
 * Модель врага в игре.
 * @property id Уникальный идентификатор врага.
 * @property name Имя врага, отображается в бою.
 * @property currentHealth Текущее здоровье. При достижении 0 враг повержен.
 * @property maxHealth Максимальное здоровье врага.
 * @property baseReward Количество золота, которое даётся за победу над этим врагом.
 * @property imageRes Ссылка на графический ресурс (например, эмодзи или путь к изображению).
 */
data class Enemy(
    val id: Int,
    val name: String,
    val currentHealth: Int,
    val maxHealth: Int,
    val baseReward: Int,
    val imageRes: String,
) {
    /**
     * true, если здоровье врага равно или меньше 0.
     */
    val isDefeated: Boolean get() = currentHealth <= 0

    /**
     * Создаёт копию врага с уменьшенным здоровьем на величину урона.
     * Здоровье не опускается ниже 0.
     * @param damage Наносимый урон.
     * @return Новый объект Enemy с обновлённым здоровьем.
     */
    fun takeDamage(damage: Int): Enemy = copy(
        currentHealth = (currentHealth - damage).coerceAtLeast(0)
    )
}