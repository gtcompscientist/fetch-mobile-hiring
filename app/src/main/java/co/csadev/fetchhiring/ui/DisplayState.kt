package co.csadev.fetchhiring.ui

import co.csadev.fetchhiring.data.DisplayType

data class DisplayState(val displayType: DisplayType, val displayLocation: Int = 0, val listId: Int = -1)
