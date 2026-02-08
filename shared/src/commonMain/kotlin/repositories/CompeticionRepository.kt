package repositories

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Competicion

class CompeticionRepository {

    private var competicion: Competicion? = null

    fun get(): Competicion? = competicion

    fun set(competicion: Competicion) {
        this.competicion = competicion
    }

    fun clear() {
        competicion = null
    }

    fun loadFromJson(json: String) {
        competicion = Json.decodeFromString(json)
    }

    fun saveToJson(): String {
        return Json.encodeToString(competicion)
    }
}
