package com.example.fantasyquestclicker.domain.utils

object NumberFormatter {
    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> "${number / 1_000_000}M"
            number >= 1_000 -> "${number / 1_000}K"
            else -> number.toString()
        }
    }
}