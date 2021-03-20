package jp.making.felix.myapplication

import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ProduceStateScope
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.lifecycle.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class NavigableScreen {
    object Home : NavigableScreen()
    object SecondScreen : NavigableScreen()
    data class DataGivenScreen(val message:String) : NavigableScreen()
}

class NavigationViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val currentScreen: MutableStateFlow<NavigableScreen> =
        MutableStateFlow(NavigableScreen.Home)

    @MainThread
    fun navigateTo(screen: NavigableScreen) {
        viewModelScope.launch {
            currentScreen.emit(screen)
        }
    }
}