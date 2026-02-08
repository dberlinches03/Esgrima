package casos

import models.*
import kotlin.random.Random
import kotlin.time.Clock

class GenerarPoules {

    fun execute(
        tiradores: List<Tirador>,
        numeroPoules: Int,
        arbitros: List<Arbitro>,
        pistasDisponibles: Int = numeroPoules
    ): List<Poule> {

        val shuffled = tiradores.shuffled()
        val poules = MutableList(numeroPoules) { mutableListOf<Tirador>() }

        shuffled.forEachIndexed { index, tirador ->
            poules[index % numeroPoules].add(tirador)
        }

        return poules.mapIndexed { index, listaTiradores ->
            Poule(
                numero = index + 1,
                asaltos = generarAsaltos(listaTiradores),
                arbitro = arbitros.getOrNull(index),
                pista = (index % pistasDisponibles) + 1
            )
        }
    }

    private fun generarAsaltos(tiradores: List<Tirador>): List<Asalto> {
        val asaltos = mutableListOf<Asalto>()
        for (i in tiradores.indices) {
            for (j in i + 1 until tiradores.size) {
                asaltos.add(
                    Asalto(
                        tirador1 = tiradores[i],
                        tirador2 = tiradores[j]
                    )
                )
            }
        }
        return asaltos
    }
}
