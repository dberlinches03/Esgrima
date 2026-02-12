package ui.arbitros

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Arbitro
import models.Address
import navigation.NavigationState
import navigation.Screen

@Composable
fun GestionArbitrosScreen(nav: NavigationState) {
    val viewModel = remember { ArbitrosViewModel() }
    val state = rememberLazyListState()
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Gestión de Árbitros", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }
            Button(onClick = { showAddDialog = true }) {
                Text("Añadir Árbitro")
            }
        }

        if (showAddDialog) {
            AddArbitroDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = {
                    viewModel.añadir(it)
                    showAddDialog = false
                }
            )
        }

        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize() // Se eliminó el padding extra de la derecha
            ) {
                items(viewModel.arbitros) { arbitro ->
                    ArbitroItem(
                        arbitro = arbitro,
                        onDelete = { viewModel.eliminar(arbitro.federateNumber) }
                    )
                }
            }

        }
    }
}

@Composable
fun ArbitroItem(arbitro: Arbitro, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${arbitro.firstName} ${arbitro.lastName}", style = MaterialTheme.typography.titleMedium)
                Text("Club: ${arbitro.club}", style = MaterialTheme.typography.bodySmall)
                Text("Federado: ${arbitro.federateNumber}", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
fun AddArbitroDialog(onDismiss: () -> Unit, onConfirm: (Arbitro) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var club by remember { mutableStateOf("") }
    var federateNumber by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir Nuevo Árbitro") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellidos") })
                OutlinedTextField(value = club, onValueChange = { club = it }, label = { Text("Club") })
                OutlinedTextField(value = federateNumber, onValueChange = { federateNumber = it }, label = { Text("Nº Federado") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val newArbitro = Arbitro(
                    firstName = firstName,
                    lastName = lastName,
                    federateNumber = federateNumber.toIntOrNull() ?: 0,
                    club = club,
                    gender = "Desconocido",
                    age = 0,
                    country = "España",
                    modality = emptyList(),
                    address = Address("", "", "", ""),
                    phoneNumbers = emptyList()
                )
                onConfirm(newArbitro)
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
