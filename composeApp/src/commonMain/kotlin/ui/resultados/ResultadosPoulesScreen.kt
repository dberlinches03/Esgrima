package ui.resultados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Poule
import navigation.NavigationState
import navigation.Screen

@Composable
fun ResultadosPoulesScreen(nav: NavigationState) {

    val vm = remember { ResultadosPoulesViewModel() }
    val competicion = vm.competicion.value

    if (competicion == null) {
        Text("No hay competición cargada.")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Resultados de Poules", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
            Text("Volver")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(vm.poules) { pouleIndex, poule ->
                PouleResultadosItem(
                    poule = poule,
                    pouleIndex = pouleIndex,
                    onUpdate = { asaltoIndex, t1, t2 ->
                        vm.actualizarAsalto(pouleIndex, asaltoIndex, t1, t2)
                    }
                )
            }
        }
    }
}

@Composable
fun PouleResultadosItem(
    poule: Poule,
    pouleIndex: Int,
    onUpdate: (asaltoIndex: Int, tocados1: Int, tocados2: Int) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {

            Text("Poule ${poule.numero}", style = MaterialTheme.typography.bodyLarge)
            Text("Árbitro: ${poule.arbitro?.firstName ?: "Sin asignar"}")
            Text("Pista: ${poule.pista}")

            Spacer(Modifier.height(10.dp))

            poule.asaltos.forEachIndexed { index, asalto ->

                var t1 by remember { mutableStateOf(asalto.tocados1.toString()) }
                var t2 by remember { mutableStateOf(asalto.tocados2.toString()) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text("${asalto.tirador1.firstName} vs ${asalto.tirador2.firstName}")
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        OutlinedTextField(
                            value = t1,
                            onValueChange = {
                                t1 = it
                                val v1 = it.toIntOrNull() ?: 0
                                val v2 = t2.toIntOrNull() ?: 0
                                onUpdate(index, v1, v2)
                            },
                            label = { Text("T1") },
                            modifier = Modifier.width(60.dp)
                        )

                        OutlinedTextField(
                            value = t2,
                            onValueChange = {
                                t2 = it
                                val v1 = t1.toIntOrNull() ?: 0
                                val v2 = it.toIntOrNull() ?: 0
                                onUpdate(index, v1, v2)
                            },
                            label = { Text("T2") },
                            modifier = Modifier.width(60.dp)
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
