package org.example.esgrima

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
        when (nav.currentScreen) {
            Screen.Login -> LoginScreen(nav)
            Screen.MenuPrincipal -> MenuPrincipalScreen(nav)
            Screen.GestionTiradores -> GestionTiradoresScreen(nav)
            Screen.GestionArbitros -> GestionArbitrosScreen(nav)
            Screen.CrearCompeticion -> CrearCompeticionScreen(nav)
            Screen.GenerarPoules -> GenerarPoulesScreen(nav)
            Screen.ResultadosPoules -> ResultadosPoulesScreen(nav)
            Screen.Clasificacion -> ClasificacionScreen(nav)
            Screen.GenerarTablon -> TablonScreen(nav)
            Screen.GuardarCargar -> GuardarCargarScreen(nav)
            else -> LoginScreen(nav) 
        }
    }
}

@Composable
fun rememberNavigation(): NavigationState {
    return androidx.compose.runtime.remember { NavigationState() }
}
