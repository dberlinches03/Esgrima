package casos

import models.*

class CalcularClasificacion {

    fun execute(poules: List<Poule>): List<ClasificacionItem> {

        val mapa = mutableMapOf<Tirador, ClasificacionItem>()

        poules.forEach { poule ->
            poule.asaltos.forEach { asalto ->
                val t1 = asalto.tirador1
                val t2 = asalto.tirador2

                val item1 = mapa.getOrPut(t1) {
                    ClasificacionItem(t1, 0, 0, 0, 0)
                }
                val item2 = mapa.getOrPut(t2) {
                    ClasificacionItem(t2, 0, 0, 0, 0)
                }

                // Actualizar tocados
                item1.tocadosDados += asalto.tocados1
                item1.tocadosRecibidos += asalto.tocados2

                item2.tocadosDados += asalto.tocados2
                item2.tocadosRecibidos += asalto.tocados1

                // Victorias
                if (asalto.tocados1 > asalto.tocados2) item1.victorias++
                if (asalto.tocados2 > asalto.tocados1) item2.victorias++
            }
        }

        // Calcular índice
        mapa.values.forEach {
            it.indice = it.tocadosDados - it.tocadosRecibidos
        }

        return mapa.values.sortedWith(
            compareByDescending<ClasificacionItem> { it.victorias }
                .thenByDescending { it.indice }
                .thenByDescending { it.tocadosDados }
        )
    }
}
