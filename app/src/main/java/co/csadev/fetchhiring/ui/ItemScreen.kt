package co.csadev.fetchhiring.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.csadev.fetchhiring.R
import co.csadev.fetchhiring.data.DisplayType
import co.csadev.fetchhiring.data.Item
import co.csadev.fetchhiring.data.ItemState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

/**
 * The Item Screen layout.
 *
 * @param itemsState UI state for the Items
 * @param refreshState Stateful value to manage showing refresh UI
 * @param onRefresh callback to trigger a refresh
 * @param displayState UI state for the display mode
 * @param tabClick callback to handle tab clicks
 * @param onMenuItemClick callback to handle menu item clicks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    itemsState: ItemState,
    refreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    displayState: DisplayState,
    tabClick: (Int, Int) -> Unit,
    onMenuItemClick: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Item List") },
                actions = {
                    // Menu Items
                    IconButton(onClick = { onMenuItemClick("refresh") }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(onClick = { onMenuItemClick("filter") }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Filter Items")
                    }
                    IconButton(onClick = { onMenuItemClick("switch_display_mode") }) {
                        Icon(Icons.Default.ViewModule, contentDescription = "Switch Display Mode")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Content of the screen
        GroupedItemsContent(
            groups = itemsState,
            refreshState = refreshState,
            onRefresh = onRefresh,
            displayState = displayState,
            tabClick = tabClick,
            modifier = Modifier.padding(
                start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                top = paddingValues.calculateTopPadding() - 32.dp, // Adjust top padding
                bottom = paddingValues.calculateBottomPadding()
            )
        )
    }
}

@Composable
fun GroupedItemsContent(
    groups: ItemState,
    refreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    displayState: DisplayState,
    tabClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.surface))
    ) {
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefresh,
            indicator = { _, _ -> }, // Not using the default indicator
            modifier = modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.surface))
        ) {
            // This is left as an `if`/`else-if` because a `when` statement with the enum values plus the extra Refresh state condition would be a risk for a future bug
            if (refreshState.isRefreshing) {
                ShowRefreshing()
            } else if (displayState.displayType == DisplayType.List) {
                ShowItemsAsList(groups)
            } else if (displayState.displayType == DisplayType.Grid) {
                ShowItemsAsGrid(groups)
            } else if (displayState.displayType == DisplayType.Tabs) {
                ShowItemsTabbed(displayState, groups, tabClick)
            }
        }
    }
}

@Composable
fun ShowItemsAsGrid(groups: ItemState) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = Modifier.fillMaxSize()
    ) {
        groups.list.forEach { group ->
            val isExpanded = expandedStates[group.listId] ?: true
            item {
                ListHeader(
                    listId = group.listId,
                    isExpanded = isExpanded,
                    groupItemSize = group.items.size,
                    onHeaderClick = { expandedStates[group.listId] = !isExpanded },
                    wrap = true
                )
            }
            if (isExpanded) {
                items(group.items.size) { itemNo ->
                    val item = group.items.elementAt(itemNo)
                    ListItemView(
                        item = item,
                        fillMaxWidth = false,
                        showEmpty = false,
                        colorScheme = itemNo % 4
                    )
                }
            }
        }
    }
}

@Composable
private fun ShowItemsTabbed(
    displayState: DisplayState,
    groups: ItemState,
    tabClick: (Int, Int) -> Unit
) {
    val selected = if (displayState.displayLocation < 0) {
        0
    } else if (displayState.displayLocation >= groups.list.size) {
        groups.list.size - 1
    } else {
        displayState.displayLocation
    }
    Column {
        TabRow(selectedTabIndex = selected) {
            groups.list.forEachIndexed { index, group ->
                Tab(
                    selected = displayState.displayLocation == index,
                    onClick = { tabClick(index, group.listId) },
                    text = { Text("List ${group.listId}") }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding()
        ) {
            val group = groups.list.firstOrNull { group -> group.listId == displayState.listId }
                ?: groups.list.first()
            items(group.items.size) { itemNo ->
                ListItemView(item = group.items.elementAt(itemNo))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShowItemsAsList(groups: ItemState) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        groups.list.forEach { group ->
            val isExpanded = expandedStates[group.listId] ?: true
            stickyHeader {
                ListHeader(
                    listId = group.listId,
                    isExpanded = isExpanded,
                    groupItemSize = group.items.size,
                    onHeaderClick = { expandedStates[group.listId] = !isExpanded },
                    wrap = false
                )
            }
            item {
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Column {
                        group.items.forEach { item ->
                            ListItemView(item = item)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowRefreshing() {
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
}


@Composable
fun ListHeader(
    listId: Int,
    isExpanded: Boolean,
    groupItemSize: Int,
    onHeaderClick: (isExpanded: Boolean) -> Unit,
    wrap: Boolean = false
) {
    val headerTypography = MaterialTheme.typography.headlineSmall.copy(
        fontSize = dimensionResource(id = R.dimen.header_text_size).value.sp
    )

    val boxModifier = Modifier
        .background(colorResource(id = R.color.secondary_container))
        .clickable { onHeaderClick(isExpanded) }
        .padding(16.dp)
    if (wrap) {
        boxModifier.wrapContentWidth()
    } else {
        boxModifier.fillMaxWidth()
    }

    Box(
        modifier = boxModifier
    ) {
        Text(
            "List ID: $listId${if (!isExpanded) " ($groupItemSize items)" else ""}",
            style = headerTypography,
            color = colorResource(id = R.color.on_secondary)
        )
    }
}

@Composable
fun ListItemView(
    item: Item,
    fillMaxWidth: Boolean = true,
    showEmpty: Boolean = true,
    colorScheme: Int = 0
) {
    val itemIdTypography = MaterialTheme.typography.bodySmall.copy(
        fontSize = dimensionResource(id = R.dimen.id_text_size).value.sp
    )

    val itemNameTypography = MaterialTheme.typography.bodySmall.copy(
        fontSize = dimensionResource(id = R.dimen.name_text_size).value.sp
    )
    val columnMod = Modifier
        .wrapContentHeight()
        .padding(dimensionResource(id = R.dimen.padding_vertical))
        .background(colorResource(id = R.color.background_color))
    if (fillMaxWidth) {
        columnMod.fillMaxWidth()
    } else {
        columnMod.wrapContentWidth()
    }
    Column(
        modifier = columnMod
    ) {
        Text(
            text = "Item ID: ${item.id}",
            style = itemIdTypography,
            color = colorResource(id = R.color.id_text_color),
            maxLines = 1
        )
        if (!item.shouldDisplayName || showEmpty) {
            Text(
                text = "Name: ${if (item.name.isNullOrBlank()) "Blank" else item.name}",
                style = itemNameTypography,
                color = colorResource(id = R.color.name_text_color),
                maxLines = 1
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.divider_padding)),
            thickness = dimensionResource(id = R.dimen.divider_thickness),
            color = colorResource(id = R.color.divider_color),
        )
    }
}
