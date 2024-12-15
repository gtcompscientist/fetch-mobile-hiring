package co.csadev.fetchhiring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.csadev.fetchhiring.ui.theme.SearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SearchTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    ItemScreen
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemSearchBar(onQueryChange: (String, Boolean) -> Unit, modifier: Modifier = Modifier) {
    var queryText by rememberSaveable { mutableStateOf("") }
    var isActive by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = queryText,
        onQueryChange = {
            queryText = it
            onQueryChange(it, false)
        },
        onSearch = {
            isActive = false
            onQueryChange(queryText, true)
            queryText = ""
        },
        active = isActive,
        onActiveChange = {},
        modifier = modifier,
        placeholder = { Text(stringResource(R.string.search_items_hint)) },
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                contentDescription = stringResource(R.string.search_items_hint),
            )
        },
        trailingIcon = {
            if (queryText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onQueryChange(queryText, true)
                        queryText = ""
                    }) {
                    Icon(
                        Icons.Rounded.Close,
                        contentDescription = stringResource(R.string.clear_search),
                    )
                }
            }
        }) {
        //
    }
}

