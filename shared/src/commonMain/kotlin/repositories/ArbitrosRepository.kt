package repositories

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Arbitro

object ArbitrosRepository {

    private val arbitros = mutableListOf<Arbitro>()

    fun getAll(): List<Arbitro> = arbitros

    fun add(arbitro: Arbitro) {
        arbitros.add(arbitro)
    }

    fun update(federateNumber: Int, updated: Arbitro) {
        val index = arbitros.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) arbitros[index] = updated
    }

    fun delete(federateNumber: Int) {
        arbitros.removeAll { it.federateNumber == federateNumber }
    }

    fun loadFromJson(json: String) {
        val list = Json.decodeFromString<List<Arbitro>>(json)
        arbitros.clear()
        arbitros.addAll(list)
    }

    fun saveToJson(): String {
        return Json.encodeToString(arbitros)
    }
}
