package co.csadev.fetchhiring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.csadev.fetchhiring.data.DisplayType
import co.csadev.fetchhiring.data.Item
import co.csadev.fetchhiring.data.ItemState
import co.csadev.fetchhiring.ui.DisplayState
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemSearchViewModel : ViewModel() {

    private val items = mutableListOf<Item>()
    private val stateFlow = MutableStateFlow(ItemState(emptyList()))

    private val refreshFlow = MutableStateFlow(SwipeRefreshState(false))

    private var displayState = DisplayState(DisplayType.List)
    private val displayFlow = MutableStateFlow(displayState)

    fun getGroupState(): StateFlow<ItemState> = stateFlow
    fun getRefreshState(): StateFlow<SwipeRefreshState> = refreshFlow
    fun getDisplayState(): StateFlow<DisplayState> = displayFlow
    fun setDisplayState(
        type: DisplayType = displayState.displayType,
        location: Int = displayState.displayLocation,
        listId: Int = displayState.listId
    ) {
        displayState = DisplayState(type, location, listId)
        displayFlow.tryEmit(displayState)
    }

    init {
        // We're emitting this before the items are retrieved and only once
        displayFlow.tryEmit(displayState)
        refresh()
    }

    fun refresh(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            stateFlow.tryEmit(ItemState(emptyList()))
            refreshFlow.tryEmit(SwipeRefreshState(true))
            items.clear()
            App.Api.getItemList(forceRefresh).let {
                items.addAll(it)
            }
            stateFlow.tryEmit(ItemState.fromList(items))
            refreshFlow.tryEmit(SwipeRefreshState(false))
        }
    }

    fun onDetach() {
        viewModelScope.cancel("ViewModel detached")
    }

}
