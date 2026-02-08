package org.example.esgrima

import java.awt.FileDialog
import java.awt.Frame
import java.io.File

actual class FilePicker actual constructor() {
    actual fun saveFile(content: String, defaultName: String) {
        val dialog = FileDialog(null as Frame?, "Guardar Competición", FileDialog.SAVE)
        dialog.file = defaultName
        dialog.isVisible = true
        
        val directory = dialog.directory
        val fileName = dialog.file
        
        if (directory != null && fileName != null) {
            File(directory, fileName).writeText(content)
        }
    }

    actual fun readFile(): String? {
        val dialog = FileDialog(null as Frame?, "Cargar Competición", FileDialog.LOAD)
        dialog.isVisible = true
        
        val directory = dialog.directory
        val fileName = dialog.file
        
        return if (directory != null && fileName != null) {
            File(directory, fileName).readText()
        } else null
    }
}
