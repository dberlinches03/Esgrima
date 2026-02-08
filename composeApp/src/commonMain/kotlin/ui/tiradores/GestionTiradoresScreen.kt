package ui.tiradores

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
import models.Tirador
import navigation.NavigationState
import navigation.Screen

@Composable
fun GestionTiradoresScreen(nav: NavigationState) {

    val viewModel = remember { TiradoresViewModel() }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Gestión de Tiradores", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }

            Button(onClick = {
                // Aquí podrías abrir un diálogo para añadir tirador
            }) {
                Text("Añadir Tirador")
            }
        }

        // Importar JSON
        OutlinedTextField(
            value = viewModel.jsonInput.value,
            onValueChange = { viewModel.jsonInput.value = it },
            label = { Text("Pegar JSON de tiradores") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.cargarDesdeJson(viewModel.jsonInput.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cargar desde JSON")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Tabla de tiradores
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.tiradores) { tirador ->
                TiradorItem(
                    tirador = tirador,
                    onDelete = { viewModel.eliminar(tirador.federateNumber) }
                )
            }
        }
    }
}

@Composable
fun TiradorItem(tirador: Tirador, onDelete: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("${tirador.firstName} ${tirador.lastName}")
                Text("Club: ${tirador.club}")
                Text("Modalidad: ${tirador.modality}")
                Text("Federado: ${tirador.federateNumber}")
            }

            Button(onClick = onDelete) {
                Text("Eliminar")
            }
        }
    }
}
