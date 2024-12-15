package co.csadev.fetchhiring.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import co.csadev.fetchhiring.R

private val LightColors: ColorScheme
    @Composable
    get() = lightColorScheme(
        primary = colorResource(id = R.color.primary),
        onPrimary = colorResource(id = R.color.on_primary),
        primaryContainer = colorResource(id = R.color.primary_container),
        onPrimaryContainer = colorResource(id = R.color.on_primary_container),
        secondary = colorResource(id = R.color.secondary),
        onSecondary = colorResource(id = R.color.on_secondary),
        secondaryContainer = colorResource(id = R.color.secondary_container),
        onSecondaryContainer = colorResource(id = R.color.on_secondary_container),
        background = colorResource(id = R.color.background_color),
        surface = colorResource(id = R.color.surface),
        onSurface = colorResource(id = R.color.on_surface),
        error = colorResource(id = R.color.error),
        onError = colorResource(id = R.color.on_error),
        errorContainer = colorResource(id = R.color.error_container),
        onErrorContainer = colorResource(id = R.color.on_error_container)
    )

private val DarkColors: ColorScheme
    @Composable
    get() = darkColorScheme(
        primary = colorResource(id = R.color.primary_dark),
        onPrimary = colorResource(id = R.color.on_primary_dark),
        primaryContainer = colorResource(id = R.color.primary_container_dark),
        onPrimaryContainer = colorResource(id = R.color.on_primary_container_dark),
        secondary = colorResource(id = R.color.secondary_dark),
        onSecondary = colorResource(id = R.color.on_secondary_dark),
        secondaryContainer = colorResource(id = R.color.secondary_container_dark),
        onSecondaryContainer = colorResource(id = R.color.on_secondary_container_dark),
        background = colorResource(id = R.color.background_color_dark),
        surface = colorResource(id = R.color.surface_dark),
        onSurface = colorResource(id = R.color.on_surface_dark),
        error = colorResource(id = R.color.error_dark),
        onError = colorResource(id = R.color.on_error_dark),
        errorContainer = colorResource(id = R.color.error_container_dark),
        onErrorContainer = colorResource(id = R.color.on_error_container_dark)
    )

@Composable
fun SearchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Automatically toggle dark theme
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
