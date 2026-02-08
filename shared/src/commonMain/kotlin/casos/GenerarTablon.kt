package casos

import models.*

class GenerarTablon {

    fun execute(clasificacion: List<ClasificacionItem>): Tablon {

        val tiradoresOrdenados = clasificacion.map { it.tirador }

        val primeraRonda = mutableListOf<Asalto>()

        var left = 0
        var right = tiradoresOrdenados.size - 1

        while (left < right) {
            primeraRonda.add(
                Asalto(
                    tirador1 = tiradoresOrdenados[left],
                    tirador2 = tiradoresOrdenados[right]
                )
            )
            left++
            right--
        }

        return Tablon(
            rondas = listOf(primeraRonda)
        )
    }
}
