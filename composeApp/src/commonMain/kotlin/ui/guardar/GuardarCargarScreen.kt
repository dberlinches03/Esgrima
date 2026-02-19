package ui.guardar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@Composable
fun GuardarCargarScreen(nav: NavigationState) {

    val vm = remember { GuardarCargarViewModel() }
    val competicion = vm.competicion.value

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Guardar / Cargar Competición", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = { nav.navigateTo(Screen.MenuPrincipal (nav.currentUserRole)) }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // GUARDAR
        Text("Guardar competición en JSON", style = MaterialTheme.typography.bodyLarge)

        Button(
            onClick = { vm.guardar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generar JSON")
        }

        OutlinedTextField(
            value = vm.jsonOutput.value,
            onValueChange = {},
            label = { Text("JSON generado") },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))


        // CARGAR
        Text("Cargar competición desde JSON", style = MaterialTheme.typography.bodyLarge)

        OutlinedTextField(
            value = vm.jsonInput.value,
            onValueChange = { vm.jsonInput.value = it },
            label = { Text("Pegar JSON aquí") },
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )

        Button(
            onClick = { vm.cargar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cargar Competición")
        }

        if (competicion != null) {
            Text("Competición cargada: ${competicion.nombre}")
        }
    }
}
