package navigation

import ui.menu.UserRole

sealed class Screen {
    object Login : Screen()
    data class MenuPrincipal(val userRole: UserRole) : Screen() // Acepta el rol como parámetro
    object GestionTiradores : Screen()
    object GestionArbitros : Screen()
    object CrearCompeticion : Screen()
    object CargarCompeticion : Screen()
    object GenerarPoules : Screen()
    object ResultadosPoules : Screen()
    object Clasificacion : Screen()
    object GenerarTablon: Screen()
    object GuardarCargar : Screen()
}
