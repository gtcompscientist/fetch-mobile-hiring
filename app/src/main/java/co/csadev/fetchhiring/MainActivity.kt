package co.csadev.fetchhiring

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import co.csadev.fetchhiring.data.DisplayType
import co.csadev.fetchhiring.data.ItemState
import co.csadev.fetchhiring.ui.DisplayState
import co.csadev.fetchhiring.ui.ItemScreen
import co.csadev.fetchhiring.ui.theme.SearchTheme
import com.google.accompanist.swiperefresh.SwipeRefreshState

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MAIN"
    }

    private val viewModel = ItemSearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchTheme {
                val itemsState by viewModel.getGroupState()
                    .collectAsState(ItemState(emptyList()))
                val refreshState by viewModel.getRefreshState()
                    .collectAsState(SwipeRefreshState(false))
                val displayState by viewModel.getDisplayState()
                    .collectAsState(DisplayState(DisplayType.List))

                ItemScreen(
                    itemsState = itemsState,
                    refreshState = refreshState,
                    onRefresh = { viewModel.refresh(false) },
                    displayState = displayState,
                    tabClick = { index, listId -> viewModel.setDisplayState(location = index, listId = listId) },
                    onMenuItemClick = { item ->
                        when (item) {
                            "refresh" -> viewModel.refresh(true)
                            "filter" -> Log.d(TAG, "Attempting to filter")
                            else -> {
                                val newState = when (displayState.displayType) {
                                    DisplayType.List -> DisplayType.Grid
                                    DisplayType.Grid -> DisplayType.Tabs
                                    DisplayType.Tabs -> DisplayType.List
                                }
                                viewModel.setDisplayState(newState)
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        viewModel.onDetach()
        super.onDestroy()
    }
}
