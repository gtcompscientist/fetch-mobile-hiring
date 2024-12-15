package co.csadev.fetchhiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import co.csadev.fetchhiring.data.ItemSearchState
import co.csadev.fetchhiring.ui.theme.SearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchTheme {
                ItemSearchScreen(
                    state = ItemSearchState(emptySet()),
                    modifier = Modifier.fillMaxSize(),
                    onQueryTextChange = { _, _ -> }
                )
            }
        }
    }
}
