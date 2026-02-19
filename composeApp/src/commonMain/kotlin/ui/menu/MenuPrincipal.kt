package ui.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import navigation.NavigationState
import navigation.Screen
import repositories.CompeticionRepository

enum class UserRole {
    ADMIN,
    ARBITRO
}

data class MenuItem(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val screen: Screen,
    val allowedRoles: List<UserRole>
)

private val allMenuItems = listOf(
    MenuItem("Crear Competición", "Configura una nueva competición", Icons.Default.EmojiEvents, Screen.CrearCompeticion, listOf(UserRole.ADMIN)),
    MenuItem("Gestionar Tiradores", "Añade o edita los participantes", Icons.Default.People, Screen.GestionTiradores, listOf(UserRole.ADMIN)),
    MenuItem("Gestionar Árbitros", "Añade o edita los árbitros", Icons.Default.Sports, Screen.GestionArbitros, listOf(UserRole.ADMIN)),
    MenuItem("Generar Poules", "Crea los grupos de la 1ª fase", Icons.Default.GroupAdd, Screen.GenerarPoules, listOf(UserRole.ADMIN)),
    MenuItem("Introducir Resultados", "Apunta los resultados de los asaltos", Icons.Default.Edit, Screen.ResultadosPoules, listOf(UserRole.ADMIN, UserRole.ARBITRO)),
    MenuItem("Ver Clasificación", "Consulta la clasificación en tiempo real", Icons.Default.Leaderboard, Screen.Clasificacion, listOf(UserRole.ADMIN, UserRole.ARBITRO)),
    MenuItem("Generar Tablón", "Crea la tabla de eliminación directa", Icons.Default.AccountTree, Screen.GenerarTablon, listOf(UserRole.ADMIN)),
    MenuItem("Guardar/Cargar", "Gestiona el estado de la competición", Icons.Default.Save, Screen.GuardarCargar, listOf(UserRole.ADMIN))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipalScreen(nav: NavigationState, userRole: UserRole) {
    val competicionActiva = CompeticionRepository.get()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Esgrima Pro v1.0 - ${userRole.name}") },
                actions = {
                    IconButton(onClick = { nav.navigateTo(Screen.Login) }) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Muestra un resumen de la competición si existe, pero no bloquea nada.
            if (competicionActiva != null) {
                InfoCompeticionCard(competicionActiva.nombre, competicionActiva.arma.toString(), competicionActiva.lugar)
                Spacer(modifier = Modifier.height(24.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(allMenuItems) { item ->
                    val isEnabled = userRole in item.allowedRoles
                    MenuItemCard(
                        item = item,
                        enabled = isEnabled,
                        onClick = { 
                            if(isEnabled) nav.navigateTo(item.screen) 
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCompeticionCard(nombre: String, arma: String, lugar: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.SportsEsports, modifier = Modifier.size(40.dp), contentDescription = null)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = nombre, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = "$arma | $lugar", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItemCard(item: MenuItem, enabled: Boolean, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth().height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (enabled) 2.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .alpha(if (enabled) 1f else 0.5f), // Sombreado si está deshabilitado
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(item.icon, contentDescription = null, modifier = Modifier.size(32.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(item.title, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            Text(item.description, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center, maxLines = 1)
        }
    }
}
