package com.brh.poc.pokemon.repository.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetail(
    val id: Long,
    val abilities: List<Ability>,
    @Json(name = "base_experience")
    val baseExperience: Long,
    val name: String,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<Type>,
    val weight: Long
)

@JsonClass(generateAdapter = true)
data class Sprites(
    @Json(name = "back_default")
    val backDefault: String,

    @Json(name = "back_female")
    val backFemale: Any? = null,

    @Json(name = "back_shiny")
    val backShiny: String,

    @Json(name = "back_shiny_female")
    val backShinyFemale: Any? = null,

    @Json(name = "front_default")
    val frontDefault: String,

    @Json(name = "front_female")
    val frontFemale: Any? = null,

    @Json(name = "front_shiny")
    val frontShiny: String,

    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any? = null,
)

@JsonClass(generateAdapter = true)
data class Ability(
    val ability: Species,

    @Json(name = "is_hidden")
    val isHidden: Boolean,

    val slot: Long
)

@JsonClass(generateAdapter = true)
data class Species(
    val name: String
)

data class GameIndex(
    @Json(name = "game_index")
    val gameIndex: Long,

    val version: Species
)

data class HeldItem(
    val item: Species,

    @Json(name = "version_details")
    val versionDetails: List<VersionDetail>
)

data class VersionDetail(
    val rarity: Long,
    val version: Species
)

data class Home(
    @Json(name = "front_default")
    val frontDefault: String,

    @Json(name = "front_female")
    val frontFemale: Any? = null,

    @Json(name = "front_shiny")
    val frontShiny: String,

    @Json(name = "front_shiny_female")
    val frontShinyFemale: Any? = null
)

@JsonClass(generateAdapter = true)
data class Stat(
    @Json(name = "base_stat")
    val baseStat: Long,

    val effort: Long,
    val stat: Species
)

@JsonClass(generateAdapter = true)
data class Type(
    val slot: Long,
    val type: Species
)
