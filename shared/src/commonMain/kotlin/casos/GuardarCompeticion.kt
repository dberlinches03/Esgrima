package casos

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Competicion

class GuardarCompeticion {

    fun execute(competicion: Competicion): String {
        return Json.encodeToString(competicion)
    }
}
