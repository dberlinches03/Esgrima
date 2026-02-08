package ui.tiradores

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
import models.Tirador
import navigation.NavigationState
import navigation.Screen

@Composable
fun GestionTiradoresScreen(nav: NavigationState) {
    val viewModel = remember { TiradoresViewModel() }
    val state = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Gestión de Tiradores", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }
        }

        // Sección de carga de JSON
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
            }
        }

        // Lista de tiradores con Scrollbar para Desktop
        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize().padding(end = 12.dp) // Espacio para la barra
            ) {
                items(viewModel.tiradores) { tirador ->
                    TiradorItem(
                        tirador = tirador,
                        onDelete = { viewModel.eliminar(tirador.federateNumber) }
                    )
                }
            }

            // Añadimos la barra de desplazamiento necesaria en Desktop
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = state)
            )
        }
    }
}

@Composable
fun TiradorItem(tirador: Tirador, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${tirador.firstName} ${tirador.lastName}", style = MaterialTheme.typography.titleMedium)
                Text("Club: ${tirador.club}", style = MaterialTheme.typography.bodySmall)
                Text("Modalidad: ${tirador.modality} | Federado: ${tirador.federateNumber}", style = MaterialTheme.typography.bodySmall)
            }

            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Eliminar")
            }
        }
    }
}
