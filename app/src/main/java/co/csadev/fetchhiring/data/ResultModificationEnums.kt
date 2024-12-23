package co.csadev.fetchhiring.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.ui.graphics.vector.ImageVector

enum class DisplayType(val icon: ImageVector, val contentDescription: String) {
    List(Icons.AutoMirrored.Filled.List, contentDescription = "List View"),
    Grid(Icons.Filled.GridView, contentDescription = "Grid View"),
    Tabs(Icons.Filled.ViewModule, contentDescription = "Tabs View")
}
