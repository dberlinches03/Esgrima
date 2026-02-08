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
import models.Address
import navigation.NavigationState
import navigation.Screen

@Composable
fun GestionTiradoresScreen(nav: NavigationState) {
    val viewModel = remember { TiradoresViewModel() }
    val state = rememberLazyListState()
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text("Gestión de Tiradores", style = MaterialTheme.typography.headlineMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal) }) {
                Text("Volver")
            }
            Button(onClick = { showAddDialog = true }) {
                Text("Añadir Tirador")
            }
        }

        if (showAddDialog) {
            AddTiradorDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = {
                    viewModel.añadir(it)
                    showAddDialog = false
                }
            )
        }

        // Lista con Scrollbar
        Box(modifier = Modifier.fillMaxSize().weight(1f)) {
            LazyColumn(
                state = state,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize().padding(end = 12.dp)
            ) {
                items(viewModel.tiradores) { tirador ->
                    TiradorItem(
                        tirador = tirador,
                        onDelete = { viewModel.eliminar(tirador.federateNumber) }
                    )
                }
            }

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState = state)
            )
        }
    }
}

@Composable
fun TiradorItem(tirador: Tirador, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${tirador.firstName} ${tirador.lastName}", style = MaterialTheme.typography.titleMedium)
                Text("Club: ${tirador.club} | Mod: ${tirador.modality}", style = MaterialTheme.typography.bodySmall)
                Text("Federado: ${tirador.federateNumber}", style = MaterialTheme.typography.bodySmall)
            }
            Button(onClick = onDelete, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text("Eliminar")
            }
        }
    }
}

@Composable
fun AddTiradorDialog(onDismiss: () -> Unit, onConfirm: (Tirador) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var club by remember { mutableStateOf("") }
    var federateNumber by remember { mutableStateOf("") }
    var modality by remember { mutableStateOf("Espada") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Añadir Nuevo Tirador") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Nombre") })
                OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Apellidos") })
                OutlinedTextField(value = club, onValueChange = { club = it }, label = { Text("Club") })
                OutlinedTextField(value = federateNumber, onValueChange = { federateNumber = it }, label = { Text("Nº Federado") })
                OutlinedTextField(value = modality, onValueChange = { modality = it }, label = { Text("Modalidad (Arma)") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val newTirador = Tirador(
                    firstName = firstName,
                    lastName = lastName,
                    club = club,
                    federateNumber = federateNumber.toIntOrNull() ?: 0,
                    modality = modality,
                    gender = "Desconocido",
                    age = 0,
                    country = "España",
                    address = Address("", "", "", ""),
                    phoneNumbers = emptyList()
                )
                onConfirm(newTirador)
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
