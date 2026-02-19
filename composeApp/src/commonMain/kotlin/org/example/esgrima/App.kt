package org.example.esgrima

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import navigation.NavigationState
import navigation.Screen
import ui.arbitros.GestionArbitrosScreen
import ui.clasificacion.ClasificacionScreen
import ui.competicion.CrearCompeticionScreen
import ui.guardar.GuardarCargarScreen
import ui.login.LoginScreen
import ui.menu.MenuPrincipalScreen
import ui.poules.GenerarPoulesScreen
import ui.resultados.ResultadosPoulesScreen
import ui.tablon.TablonScreen
import ui.tiradores.GestionTiradoresScreen

@Composable
fun App() {
    MaterialTheme {
        val nav = rememberNavigation()
        
        when (val current = nav.currentScreen) {
            is Screen.Login -> LoginScreen(nav)
            is Screen.MenuPrincipal -> MenuPrincipalScreen(nav, current.userRole)
            is Screen.GestionTiradores -> GestionTiradoresScreen(nav)
            is Screen.GestionArbitros -> GestionArbitrosScreen(nav)
            is Screen.CrearCompeticion -> CrearCompeticionScreen(nav)
            is Screen.GenerarPoules -> GenerarPoulesScreen(nav)
            is Screen.ResultadosPoules -> ResultadosPoulesScreen(nav)
            is Screen.Clasificacion -> ClasificacionScreen(nav)
            is Screen.GenerarTablon -> TablonScreen(nav)
            is Screen.GuardarCargar -> GuardarCargarScreen(nav)
            else -> LoginScreen(nav)
        }
    }
}

@Composable
fun rememberNavigation(): NavigationState {
    return androidx.compose.runtime.remember { NavigationState() }
}
