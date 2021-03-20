package jp.making.felix.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import jp.making.felix.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : ComponentActivity() {
    lateinit var viewModel: NavigationViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = NavigationViewModel(savedStateHandle = SavedStateHandle())
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Column {
                    LiveGreeting(viewModel = viewModel)
                    Button(onClick = {
                        when (viewModel.currentScreen.value) {
                            is NavigableScreen.Home -> viewModel.navigateTo(NavigableScreen.SecondScreen)
                            is NavigableScreen.SecondScreen -> viewModel.navigateTo(NavigableScreen.DataGivenScreen("aaa"))
                            is NavigableScreen.DataGivenScreen -> viewModel.navigateTo(NavigableScreen.Home)
                        }
                        Log.d("currentScreen", viewModel.currentScreen.value.toString())
                    }) {

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun LiveGreeting(viewModel: NavigationViewModel) {
    val screenState = viewModel.currentScreen.collectAsState()

    screenState.value.let {
        when (it) {
            is NavigableScreen.Home -> Greeting(name = "HOME!!")
            is NavigableScreen.SecondScreen -> Greeting(name = "SECOND!!")
            is NavigableScreen.DataGivenScreen -> Greeting(name = it.message)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}