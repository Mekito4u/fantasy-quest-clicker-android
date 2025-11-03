# Fantasy Quest Clicker

Мобильное RPG-приложение в жанре кликер, разрабатываемое на Kotlin для Android.

## 🎯 Текущий статус проекта

**✅ Реализовано:**

- Чистая архитектура (UI Layer - Domain Layer - Data Layer)
- Полностью рабочая боевая система с тап-управлением
- Система экономики (золото за победу над врагами)
- Случайная генерация врагов (Гоблин, Орк, Скелет)
- Сохранение прогресса через DataStore
- Кастомные UI компоненты (HealthBar, BaseScreen)
- Форматирование чисел (1K, 1M)

**🎮 Игровой процесс:**

- Тап по врагу для атаки
- Health Bar с цветовой индикацией
- Смена врагов при победе
- Начисление золота
- Сохранение прогресса между сессиями

## 🏗️ Архитектура

### Clean Architecture + MVVM

### Ключевые компоненты:

**🎨 UI Layer:**

- `BattleScreen` - главный экран боя
- `BattleViewModel` - управление состоянием боя
- `HealthBar` - кастомный компонент здоровья
- `BaseScreen` - базовые шаблоны экранов

**🧠 Domain Layer:**

- `AttackEnemyUseCase` - бизнес-логика атаки
- `Player`, `Enemy` - доменные модели
- `PlayerRepository` - интерфейс доступа к данным

**💾 Data Layer:**

- `PlayerDataStoreRepository` - реализация с DataStore
- `DataStore<Preferences>` - локальное хранилище

## 🛠 Технологический стек

- **Язык:** Kotlin + Coroutines + Flow
- **Минимальная SDK:** Android 8.0 (API 26)
- **Архитектура:** Clean Architecture + MVVM
- **UI:** Jetpack Compose + Material3
- **Локальная БД:** DataStore (Preferences)
- **Сборка:** Gradle с Kotlin DSL

## 📊 Структура проекта

```
FantasyQuestClicker/
├── 📱 app/ (Android Module)
│   ├── 📁 src/main/java/com/example/fantasyquestclicker/
│   │   ├── 🎨 ui/ (Presentation Layer)
│   │   │   ├── theme/ (Material3 темы)
│   │   │   ├── screens/ (Экраны)
│   │   │   │   └── BattleScreen.kt
│   │   │   ├── viewmodels/ (ViewModels)
│   │   │   │   └── BattleViewModel.kt
│   │   │   └── components/ (UI компоненты)
│   │   │       └── HealthBar.kt
│   │   ├── 🧠 domain/ (Business Logic Layer)
│   │   │   ├── models/ (Data классы)
│   │   │   │   ├── Player.kt
│   │   │   │   └── Enemy.kt
│   │   │   ├── use_cases/ (Use Cases)
│   │   │   │   └── AttackEnemyUseCase.kt
│   │   │   └── repositories/ (Интерфейсы репозиториев)
│   │   │       └── PlayerRepository.kt
│   │   └── 💾 data/ (Data Layer)
│   │       └── repositories/ (Реализации репозиториев)
│   │           └── PlayerDataStoreRepository.kt
│   └── 📁 build.gradle.kts (Зависимости)
├── 📄 README.md
└── 📄 build.gradle.kts (Project config)
```

## 📈 Дорожная карта

- [x] Базовая архитектура и боевая система
- [x] Система сохранения прогресса (DataStore)
- [ ] Система прокачки навыков
- [ ] Система квестов и достижений
- [ ] Юнит-тестирование
- [ ] Балансировка игровых параметров
- [ ] Полировка UI/UX

