package ui.competicion

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCompeticionScreen(nav: NavigationState) {
    val vm = remember { CrearCompeticionViewModel() }
    val tiradoresState = rememberLazyListState()
    val arbitrosState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Crear Nueva Competición", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }
        }

        // Formulario Principal en una Card
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = vm.nombre.value, onValueChange = { vm.nombre.value = it }, label = { Text("Nombre de la competición") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = vm.entidadOrganizadora.value, onValueChange = { vm.entidadOrganizadora.value = it }, label = { Text("Entidad Organizadora") }, modifier = Modifier.fillMaxWidth())
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = vm.fecha.value, onValueChange = { vm.fecha.value = it }, label = { Text("Fecha (DD/MM/AAAA)") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = vm.lugar.value, onValueChange = { vm.lugar.value = it }, label = { Text("Lugar/Sede") }, modifier = Modifier.weight(1f))
                }

                Text("Seleccionar Arma:", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("espada", "florete", "sable").forEach { arma ->
                        FilterChip(
                            selected = vm.arma.value == arma,
                            onClick = { vm.arma.value = arma },
                            label = { Text(arma.uppercase()) }
                        )
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth().weight(1f), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            // Columna Tiradores
            Column(modifier = Modifier.weight(1f)) {
                Text("Tiradores: ${vm.tiradoresSeleccionados.size} de ${vm.tiradores.size}", style = MaterialTheme.typography.titleMedium)
                Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                    LazyColumn(state = tiradoresState, modifier = Modifier.fillMaxSize().padding(end = 12.dp)) {
                        items(vm.tiradores) { t ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                Checkbox(checked = vm.tiradoresSeleccionados.contains(t), onCheckedChange = { vm.toggleTirador(t) })
                                Text("${t.firstName} ${t.lastName}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(tiradoresState)
                    )
                }
            }

            // Columna Árbitros
            Column(modifier = Modifier.weight(1f)) {
                Text("Árbitros: ${vm.arbitrosSeleccionados.size} de ${vm.arbitros.size}", style = MaterialTheme.typography.titleMedium)
                Box(modifier = Modifier.fillMaxSize().weight(1f)) {
                    LazyColumn(state = arbitrosState, modifier = Modifier.fillMaxSize().padding(end = 12.dp)) {
                        items(vm.arbitros) { a ->
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                                Checkbox(checked = vm.arbitrosSeleccionados.contains(a), onCheckedChange = { vm.toggleArbitro(a) })
                                Text("${a.firstName} ${a.lastName}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    VerticalScrollbar(
                        modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                        adapter = rememberScrollbarAdapter(arbitrosState)
                    )
                }
            }
        }

        Button(
            onClick = {
                vm.crearCompeticion()
                nav.navigateTo(Screen.GenerarPoules)
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            enabled = vm.nombre.value.isNotBlank() && vm.tiradoresSeleccionados.isNotEmpty()
        ) {
            Text("FINALIZAR Y GENERAR POULES")
        }
    }
}
