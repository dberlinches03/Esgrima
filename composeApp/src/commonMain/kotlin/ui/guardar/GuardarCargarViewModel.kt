package ui.guardar

import androidx.compose.runtime.mutableStateOf
import models.Competicion
import repositories.CompeticionRepository
import casos.CargarCompeticion
import casos.GuardarCompeticion

class GuardarCargarViewModel(
    private val compRepo: CompeticionRepository = CompeticionRepository(),
    private val guardarUseCase: GuardarCompeticion = GuardarCompeticion(),
    private val cargarUseCase: CargarCompeticion = CargarCompeticion()
) {

    val competicion = mutableStateOf<Competicion?>(null)
    val jsonOutput = mutableStateOf("")
    val jsonInput = mutableStateOf("")

    init {
        competicion.value = compRepo.get()
    }

    fun guardar() {
        val comp = competicion.value ?: return
        jsonOutput.value = guardarUseCase.execute(comp)
    }

    fun cargar() {
        val comp = cargarUseCase.execute(jsonInput.value)
        compRepo.set(comp)
        competicion.value = comp
    }
}
