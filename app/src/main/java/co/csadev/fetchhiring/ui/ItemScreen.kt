package co.csadev.fetchhiring.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import co.csadev.fetchhiring.R
import co.csadev.fetchhiring.data.Item
import co.csadev.fetchhiring.data.ItemState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

/**
 * The Item Screen layout.
 *
 * @param itemsState UI state for the Items
 * @param refreshState Stateful value to manage showing refresh UI
 * @param modifier the modifier to apply to this layout.
 * @param onRefresh callback to trigger a refresh
 */
@Composable
fun ItemScreen(
    itemsState: ItemState,
    refreshState: SwipeRefreshState,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface))
    ) {
//        ItemSearchBar(
//            onQueryChange = onQueryTextChange,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//        )
//        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefresh,
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.surface))
        ) {
            if (refreshState.isRefreshing) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
                        Text(text = "Loading items...")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(itemsState.list.size) { result ->
                        ListItemView(
                            item = itemsState.list.elementAt(result)
                        )
                    }
//            state.list.forEach { item ->
//                stickyHeader {
//                    ListHeader(listId = group.listId)
//                }
//
//                items(group.items.size) { itemNo ->
//                    ListItemView(item = group.items.elementAt(itemNo))
//                }
//            }
                }
            }
        }
    }
}


@Composable
fun ListHeader(listId: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.secondary_container)) // Sticky header background color
            .padding(16.dp)
    ) {
        Text(
            "List ID: $listId",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.on_secondary)
        )
    }
}

@Composable
fun ListItemView(item: Item) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(dimensionResource(id = R.dimen.padding_horizontal))
    ) {
        Text(
            text = "Item ID: ${item.id}",
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.on_surface)
        )
        Text(
            text = "Name: ${item.name ?: "Unnamed"}",
            style = MaterialTheme.typography.bodySmall,
            color = colorResource(id = R.color.on_surface)
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_vertical)),
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = colorResource(id = R.color.divider_color)
        )
    }
}
