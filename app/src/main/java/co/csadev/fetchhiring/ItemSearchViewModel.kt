package co.csadev.fetchhiring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.csadev.fetchhiring.data.Item
import co.csadev.fetchhiring.data.ItemState
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemSearchViewModel : ViewModel() {

    private val items = mutableListOf<Item>()
    private val searchState = ItemState(emptyList())
    private val stateFlow = MutableStateFlow(searchState)

    private val refreshState = SwipeRefreshState(false)
    private val refreshFlow = MutableStateFlow(refreshState)

    fun getItemState(): StateFlow<ItemState> = stateFlow
    fun getRefreshState(): StateFlow<SwipeRefreshState> = refreshFlow

    init {
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
            stateFlow.tryEmit(ItemState(items))
            refreshFlow.tryEmit(SwipeRefreshState(false))
        }
    }

    fun onDetach() {
        viewModelScope.cancel("ViewModel detached")
    }
}
