package navigation

sealed class Screen {
    object Login : Screen()
    object MenuPrincipal : Screen()
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