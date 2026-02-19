package ui.clasificacion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@Composable
fun ClasificacionScreen(nav: NavigationState) {

    val vm = remember { ClasificacionViewModel() }
    val competicion = vm.competicion.value

    if (competicion == null) {
        Text("No hay competición cargada.")
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Clasificación Final de Poules", style = MaterialTheme.typography.bodyMedium)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = { nav.navigateTo(Screen.MenuPrincipal (nav.currentUserRole)) }) {
                Text("Volver")
            }

            Button(onClick = { nav.navigateTo(Screen.GenerarTablon) }) {
                Text("Generar Tablón Eliminatorio")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(vm.clasificacion) { index, item ->
                ClasificacionItemRow(
                    posicion = index + 1,
                    item = item
                )
            }
        }
    }
}

@Composable
fun ClasificacionItemRow(posicion: Int, item: models.ClasificacionItem) {

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text("$posicion. ${item.tirador.firstName} ${item.tirador.lastName}")
                Text("Club: ${item.tirador.club}")
            }

            Column(horizontalAlignment = androidx.compose.ui.Alignment.End) {
                Text("V: ${item.victorias}")
                Text("TD: ${item.tocadosDados}")
                Text("TR: ${item.tocadosRecibidos}")
                Text("Índice: ${item.indice}")
            }
        }
    }
}
