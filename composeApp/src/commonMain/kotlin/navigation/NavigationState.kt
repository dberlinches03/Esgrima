package navigation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class NavigationState {

    var currentScreen by mutableStateOf<Screen>(Screen.Login)
        private set

    fun navigateTo(screen: Screen) {
        currentScreen = screen
    }
}
