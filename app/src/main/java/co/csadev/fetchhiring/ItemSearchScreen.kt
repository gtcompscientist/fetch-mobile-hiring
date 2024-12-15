package co.csadev.fetchhiring

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import co.csadev.fetchhiring.data.ItemSearchState

/**
 * The User Search Screen layout.
 *
 * @param state Ui state for the User Search Screen.
 * @param modifier the modifier to apply to this layout.
 * @param onQueryTextChange the callback to be invoked when the search query changes.
 */
@Composable
fun ItemSearchScreen (
    state: ItemSearchState,
    modifier: Modifier = Modifier,
    onQueryTextChange: (String, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface))
    ) {
        ItemSearchBar(
            onQueryChange = onQueryTextChange,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.listGroups.size) { result ->
                val element = state.listGroups.elementAt(result)
                ItemRow(0, 0, "")
            }
        }
    }
}
