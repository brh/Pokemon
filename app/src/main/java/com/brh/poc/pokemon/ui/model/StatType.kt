package com.brh.poc.pokemon.ui.model

sealed class StatType(val special: Boolean = false) {
    class Attack(special: Boolean): StatType(special)
    class Defense(special: Boolean): StatType(special)
    class Speed(val value: Int): StatType()
}
