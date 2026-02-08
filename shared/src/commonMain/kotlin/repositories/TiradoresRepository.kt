package repositories

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Tirador

class TiradoresRepository {

    private val tiradores = mutableListOf<Tirador>()

    fun getAll(): List<Tirador> = tiradores

    fun add(tirador: Tirador) {
        tiradores.add(tirador)
    }

    fun update(federateNumber: Int, updated: Tirador) {
        val index = tiradores.indexOfFirst { it.federateNumber == federateNumber }
        if (index != -1) tiradores[index] = updated
    }

    fun delete(federateNumber: Int) {
        tiradores.removeAll { it.federateNumber == federateNumber }
    }

    fun loadFromJson(json: String) {
        val list = Json.decodeFromString<List<Tirador>>(json)
        tiradores.clear()
        tiradores.addAll(list)
    }

    fun saveToJson(): String {
        return Json.encodeToString(tiradores)
    }
}
