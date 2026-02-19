package navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ui.menu.UserRole

class NavigationState {
    var currentScreen by mutableStateOf<Screen>(Screen.Login)
        private set

    // Guardamos el rol globalmente para no tener que pasarlo en cada pantalla
    var currentUserRole by mutableStateOf(UserRole.ADMIN)
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }

    fun loginAs(role: UserRole) {
        currentUserRole = role
        currentScreen = Screen.MenuPrincipal(role)
    }
}
