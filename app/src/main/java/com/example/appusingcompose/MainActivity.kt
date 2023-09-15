package com.example.appusingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appusingcompose.screens.QuoteDetailScreen
import com.example.appusingcompose.screens.QuoteListScreen
import com.example.appusingcompose.ui.theme.AppUsingComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            delay(8000)
            DataManager.loadAssetsFromFile(applicationContext)
        }
        setContent {
            AppTheme()
//            Text(text = "Hello new Hello world!!")
//            AppUsingComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
//            }
        }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//
//        DataManager.switchPages(DataManager.currentQuote)
//    }
}

@Preview
@Composable
private fun AppTheme() {
//    LocalConfiguration.current.colorMode - to acess theme related things
//     LocalContext.current  -  to acess context
//     LocalContext.current.applicationContext  -  to acess application context
    var theme = remember { mutableStateOf(false) }

    AppUsingComposeTheme(theme.value) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Text(text = "Demo theme", style = MaterialTheme.typography.headlineMedium)
                Button(onClick = {
                    theme.value = !theme.value
                }) {
                    Text(text = "Change theme")
                }
                App()
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun App() {
    if (DataManager.isDataLoaded.value) {
        if (DataManager.currentPage.value == Pages.LISTING) {
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        } else {
            DataManager.currentQuote?.let { QuoteDetailScreen(quote = it) }
        }
    } else {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize(1f)
        ) {
//            Text(
//                text = "Loading....",
//                style = MaterialTheme.typography.headlineLarge
//            )
            loader()
        }
    }

}
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AppUsingComposeTheme {
//        Greeting("Android")
//    }
//}

@Composable
fun loader() {
    val degree = produceState(initialValue = 0) {
        while (true) {
            delay(16)
            value = (value + 30) % 360
        }
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(1f),
        content = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Loading",
                    modifier = Modifier
                        .size(60.dp)
                        .rotate(degree.value.toFloat())
                )
                Text(text = "Loading")
            }
        })
}


enum class Pages {
    LISTING,
    DETAIL
}