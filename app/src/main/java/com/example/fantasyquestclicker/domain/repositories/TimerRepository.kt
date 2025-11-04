package com.example.fantasyquestclicker.domain.repositories

interface TimerRepository {
    fun startTimer(intervalMs: Long, onTick: () -> Unit)
    fun stopTimer()
    fun isTimerRunning(): Boolean
}