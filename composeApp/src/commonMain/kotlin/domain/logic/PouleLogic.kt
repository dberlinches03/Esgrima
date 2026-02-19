package domain.logic

import domain.model.Asalto
import domain.model.Poule
import domain.model.Tirador

object PouleLogic {

    /**
     * Divide a los tiradores en el número especificado de poules de forma aleatoria.
     */
    fun generarPoules(
        tiradores: List<Tirador>,
        numPoules: Int
    ): List<Poule> {
        if (tiradores.isEmpty() || numPoules <= 0) return emptyList()

        // 1. Mezclar los tiradores para un reparto aleatorio y equitativo.
        val tiradoresMezclados = tiradores.shuffled()
        
        val grupos = MutableList(numPoules) { mutableListOf<Tirador>() }
        
        // 2. Repartir los tiradores uno a uno en los grupos.
        var indiceGrupo = 0
        for (tirador in tiradoresMezclados) {
            grupos[indiceGrupo].add(tirador)
            indiceGrupo = (indiceGrupo + 1) % numPoules
        }

        // 3. Crear los objetos Poule y generar sus asaltos.
        return grupos.mapIndexed { index, listaTiradores ->
            val pouleId = (index + 1).toString()
            Poule(
                id = pouleId,
                tiradores = listaTiradores,
                asaltos = generarAsaltosRoundRobin(pouleId, listaTiradores)
            )
        }
    }

    /**
     * Genera todos los asaltos posibles entre los tiradores de una poule (todos contra todos).
     */
    private fun generarAsaltosRoundRobin(pouleId: String, tiradores: List<Tirador>): List<Asalto> {
        val asaltos = mutableListOf<Asalto>()
        if (tiradores.size < 2) return asaltos

        for (i in 0 until tiradores.size) {
            for (j in i + 1 until tiradores.size) {
                asaltos.add(
                    Asalto(
                        id = "${pouleId}_${i}_${j}",
                        tirador1 = tiradores[i],
                        tirador2 = tiradores[j]
                    )
                )
            }
        }
        // Mezclamos los asaltos para que el orden sea variado.
        return asaltos.shuffled()
    }
}
