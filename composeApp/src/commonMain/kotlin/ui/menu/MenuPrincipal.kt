package ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen

// Definimos los botones como una lista de pares (Texto, Destino)
private val menuItems = listOf(
    "Gestionar Tiradores" to Screen.GestionTiradores,
    "Gestionar Árbitros" to Screen.GestionArbitros,
    "Crear Competición" to Screen.CrearCompeticion,
    "Generar Poules" to Screen.GenerarPoules,
    "Resultados Poules" to Screen.ResultadosPoules,
    "Clasificación" to Screen.Clasificacion,
    "Generar Tablón" to Screen.GenerarTablon,
    "Guardar/Cargar" to Screen.GuardarCargar
)

@Composable
fun MenuPrincipalScreen(nav: NavigationState) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Menú Principal", style = MaterialTheme.typography.headlineLarge, modifier = Modifier.padding(bottom = 32.dp))

        // Usamos una Grid para que se vea mejor en pantallas grandes
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 200.dp), // Se adapta al tamaño, mínimo 200dp por botón
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(menuItems) { (text, screen) ->
                Button(
                    onClick = { nav.navigateTo(screen) },
                    modifier = Modifier.height(60.dp).fillMaxWidth()
                ) {
                    Text(text)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { nav.navigateTo(Screen.Login) }) {
            Text("Cerrar Sesión")
        }
    }
}
