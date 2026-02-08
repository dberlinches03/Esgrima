package ui.poules

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun GenerarPoulesScreen(nav: NavigationState) {

    val vm = remember { GenerarPoulesViewModel() }
    val competicion = vm.competicion.value

    if (competicion == null) {
        Text("No hay competición cargada.")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Generación de Poules", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
            Text("Volver")
        }

        Text("Tiradores inscritos: ${competicion.tiradores.size}")
        Text("Árbitros disponibles: ${competicion.arbitros.size}")

        // Selección del número de poules
        OutlinedTextField(
            value = vm.numeroPoules.value.toString(),
            onValueChange = {
                vm.numeroPoules.value = it.toIntOrNull() ?: 1
            },
            label = { Text("Número de poules") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { vm.generarPoules() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generar Poules")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar poules generados
        if (vm.poulesGenerados.isNotEmpty()) {
            Text("Poules generados:", style = MaterialTheme.typography.bodyLarge)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(vm.poulesGenerados) { poule ->
                    PouleItem(poule)
                }
            }
        }
    }
}

@Composable
fun PouleItem(poule: Poule) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(15.dp)) {

            Text("Poule ${poule.numero}", style = MaterialTheme.typography.bodyLarge)
            Text("Árbitro: ${poule.arbitro?.firstName ?: "Sin asignar"}")
            Text("Pista: ${poule.pista}")

            Spacer(Modifier.height(10.dp))

            Text("Asaltos:")

            poule.asaltos.forEach { asalto ->
                Text("- ${asalto.tirador1.firstName} vs ${asalto.tirador2.firstName}")
            }
        }
    }
}
