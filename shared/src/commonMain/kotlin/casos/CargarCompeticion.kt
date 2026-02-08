package casos

import kotlinx.serialization.json.Json
import models.Competicion

class CargarCompeticion {

    fun execute(json: String): Competicion {
        return Json.decodeFromString(json)
    }
}
