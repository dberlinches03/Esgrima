package ui.guardar

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import models.Competicion
import org.example.esgrima.FilePicker
import repositories.CompeticionRepository

class GuardarCargarViewModel : ViewModel() {

    val jsonOutput = mutableStateOf("")
    val jsonInput = mutableStateOf("")
    val competicion = mutableStateOf<Competicion?>(null)
    
    private val filePicker = FilePicker()

    init {
        actualizarEstado()
    }

    private fun actualizarEstado() {
        competicion.value = CompeticionRepository.get()
    }

    fun guardarEnArchivo() {
        val json = CompeticionRepository.saveToJson()
        filePicker.saveFile(json, "competicion.json")
    }

    fun cargarDesdeArchivo() {
        val json = filePicker.readFile()
        if (json != null) {
            CompeticionRepository.loadFromJson(json)
            actualizarEstado()
        }
    }

    fun guardar() {
        jsonOutput.value = CompeticionRepository.saveToJson()
    }

    fun cargar() {
        if (jsonInput.value.isNotBlank()) {
            CompeticionRepository.loadFromJson(jsonInput.value)
            actualizarEstado()
        }
    }
}
