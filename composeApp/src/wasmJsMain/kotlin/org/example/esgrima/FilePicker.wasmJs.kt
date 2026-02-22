package org.example.esgrima

import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.get

actual class FilePicker actual constructor() {
    actual fun saveFile(content: String, defaultName: String) {
        val element = document.createElement("a") as HTMLAnchorElement
        element.setAttribute("href", "data:text/plain;charset=utf-8," + encodeURIComponent(content))
        element.setAttribute("download", defaultName)
        element.style.display = "none"
        document.body?.appendChild(element)
        element.click()
        document.body?.removeChild(element)
    }

    actual fun readFile(): String? {
        // La lectura de archivos en web es asíncrona y más compleja de devolver síncronamente.
        // Para que compile, podemos dejar un aviso o implementar una versión simplificada.
        // Nota: En una app real de producción para web, se usarían callbacks o suspend.
        println("Lectura de archivos no implementada síncronamente en Web")
        return null
    }
}

private fun encodeURIComponent(s: String): String = s // Simplificación para el prototipo
