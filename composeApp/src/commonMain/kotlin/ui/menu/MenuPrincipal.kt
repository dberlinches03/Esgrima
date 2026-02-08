package ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

@Composable
fun MenuPrincipalScreen(nav: NavigationState) {

    Column(
        modifier = Modifier.fillMaxSize().padding(40.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Menú Principal", style = MaterialTheme.typography.bodyMedium)

        Button(onClick = { nav.navigateTo(Screen.GestionTiradores) }) {
            Text("Gestión de Tiradores")
        }

        Button(onClick = { nav.navigateTo(Screen.GestionArbitros) }) {
            Text("Gestión de Árbitros")
        }

        Button(onClick = { nav.navigateTo(Screen.CrearCompeticion) }) {
            Text("Crear Competición")
        }

        Button(onClick = { nav.navigateTo(Screen.CargarCompeticion) }) {
            Text("Cargar Competición")
        }
    }
}
