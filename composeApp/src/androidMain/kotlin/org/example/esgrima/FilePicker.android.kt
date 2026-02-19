package org.example.esgrima

actual class FilePicker actual constructor() {
    actual fun saveFile(content: String, defaultName: String) {
        // En Android esto requiere SAF o permisos de MediaStore
        // Por ahora lo dejamos vacío para que compile
    }

    actual fun readFile(): String? {
        // En Android esto requiere lanzar un Intent de selección de archivos
        return null
    }
}
