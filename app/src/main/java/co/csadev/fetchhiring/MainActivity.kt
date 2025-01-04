package co.csadev.fetchhiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import co.csadev.fetchhiring.data.ItemState
import co.csadev.fetchhiring.ui.ItemScreen
import co.csadev.fetchhiring.ui.theme.SearchTheme
import com.google.accompanist.swiperefresh.SwipeRefreshState

class MainActivity : ComponentActivity() {

    private val viewModel = ItemSearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchTheme {
                val state by viewModel.getItemState()
                    .collectAsState(ItemState(emptyList()))
                val refreshState by viewModel.getRefreshState()
                    .collectAsState(SwipeRefreshState(false))

                ItemScreen(
                    itemsState = state,
                    refreshState = refreshState,
                    modifier = Modifier.fillMaxSize(),
                    onRefresh = { viewModel.refresh(true) }
                )
            }
        }
    }

    override fun onDestroy() {
        viewModel.onDetach()
        super.onDestroy()
    }
}
