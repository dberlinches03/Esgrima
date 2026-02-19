package ui.resultados

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import models.Poule
import navigation.NavigationState
import navigation.Screen

@Composable
fun ResultadosPoulesScreen(nav: NavigationState) {
    val vm = remember { ResultadosPoulesViewModel() }
    val competicion = vm.competicion.value

    if (competicion == null) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Warning, contentDescription = null, modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(16.dp))
                Text(
                    "No hay ninguna competición cargada.",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Para introducir resultados, primero debes crear o cargar un evento.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { nav.navigateTo(Screen.MenuPrincipal(nav.currentUserRole)) }) {
                        Text("Volver al Menú")
                    }
                    Button(
                        onClick = { nav.navigateTo(Screen.CrearCompeticion) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Crear Competición")
                    }
                }
            }
        }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Resultados de Poules", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = { nav.navigateTo(Screen.MenuPrincipal(nav.currentUserRole)) }) {
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
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text("Poule ${poule.numero}", style = MaterialTheme.typography.titleLarge)
            Text("Árbitro: ${poule.arbitro?.firstName ?: "Sin asignar"}")
            Text("Pista: ${poule.pista}")

            Spacer(Modifier.height(10.dp))

            poule.asaltos.forEachIndexed { index, asalto ->
                var t1 by remember { mutableStateOf(asalto.tocados1.toString()) }
                var t2 by remember { mutableStateOf(asalto.tocados2.toString()) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${asalto.tirador1.firstName} vs ${asalto.tirador2.firstName}", modifier = Modifier.weight(1f))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = t1,
                            onValueChange = {
                                t1 = it
                                it.toIntOrNull()?.let { v1 -> onUpdate(index, v1, t2.toIntOrNull() ?: 0) }
                            },
                            label = { Text("T1") },
                            modifier = Modifier.width(70.dp)
                        )
                        OutlinedTextField(
                            value = t2,
                            onValueChange = {
                                t2 = it
                                it.toIntOrNull()?.let { v2 -> onUpdate(index, t1.toIntOrNull() ?: 0, v2) }
                            },
                            label = { Text("T2") },
                            modifier = Modifier.width(70.dp)
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}
