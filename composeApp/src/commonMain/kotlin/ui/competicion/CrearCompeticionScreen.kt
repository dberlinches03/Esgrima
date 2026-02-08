package ui.competicion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@Composable
fun CrearCompeticionScreen(nav: NavigationState) {

    val vm = remember { CrearCompeticionViewModel() }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Crear Competición", style = MaterialTheme.typography.bodyMedium)

        // Botón volver
        Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
            Text("Volver")
        }

        // Formulario
        OutlinedTextField(
            value = vm.nombre.value,
            onValueChange = { vm.nombre.value = it },
            label = { Text("Nombre de la competición") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.entidadOrganizadora.value,
            onValueChange = { vm.entidadOrganizadora.value = it },
            label = { Text("Entidad organizadora") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.fecha.value,
            onValueChange = { vm.fecha.value = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vm.lugar.value,
            onValueChange = { vm.lugar.value = it },
            label = { Text("Lugar") },
            modifier = Modifier.fillMaxWidth()
        )

        // Selección de arma
        Text("Arma:")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            listOf("espada", "florete", "sable").forEach { arma ->
                Button(
                    onClick = { vm.arma.value = arma },
                ) {
                    Text(arma)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Selección de tiradores
        Text("Seleccionar Tiradores", style = MaterialTheme.typography.bodyLarge)

        LazyColumn(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(vm.tiradores) { t ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${t.firstName} ${t.lastName} (${t.modality})")
                    Checkbox(
                        checked = vm.tiradoresSeleccionados.contains(t),
                        onCheckedChange = { vm.toggleTirador(t) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))


        // Selección de árbitros
        Text("Seleccionar Árbitros", style = MaterialTheme.typography.bodyLarge)

        LazyColumn(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(vm.arbitros) { a ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${a.firstName} ${a.lastName} (${a.modality.joinToString()})")
                    Checkbox(
                        checked = vm.arbitrosSeleccionados.contains(a),
                        onCheckedChange = { vm.toggleArbitro(a) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botón crear competición
        Button(
            onClick = {
                vm.crearCompeticion()
                nav.navigateTo(Screen.CrearCompeticion) // o siguiente pantalla
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear Competición")
        }
    }
}
