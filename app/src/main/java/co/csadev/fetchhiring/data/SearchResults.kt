package co.csadev.fetchhiring.data


/**
 * Models Items returned by the API and used in the app in a stateful manner for reliability
 */
data class ItemState(val list: List<Item> = emptyList())
