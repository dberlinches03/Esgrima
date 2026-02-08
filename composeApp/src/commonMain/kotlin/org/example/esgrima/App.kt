import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import navigation.NavigationState
import navigation.Screen
import ui.arbitros.GestionArbitrosScreen
import ui.competicion.CrearCompeticionScreen
import ui.login.LoginScreen
import ui.menu.MenuPrincipalScreen
import ui.poules.GenerarPoulesScreen
import ui.tiradores.GestionTiradoresScreen

@Composable
fun App() {
    MaterialTheme {
        val nav = rememberNavigation()
        when (nav.currentScreen) {
            Screen.Login -> LoginScreen(nav)
            Screen.MenuPrincipal -> MenuPrincipalScreen(nav)
            Screen.GestionTiradores -> GestionTiradoresScreen(nav)
            Screen.GestionArbitros -> GestionArbitrosScreen(nav)
            Screen.CrearCompeticion -> CrearCompeticionScreen(nav)
            Screen.GenerarPoules -> GenerarPoulesScreen(nav)
            else -> {TODO()}
        }
    }
}

@Composable
fun rememberNavigation(): NavigationState {
    return androidx.compose.runtime.remember { NavigationState() }
}
