package co.csadev.fetchhiring.data

/**
 * Models Items returned by the API and used in the app in a stateful manner for reliability
 */
data class ItemState(val list: List<GroupedItems> = emptyList()) {
    companion object {
        fun fromList(items: List<Item>) =
            ItemState(items.groupBy { it.listId }
                .map {
                    GroupedItems(
                        it.key,
                        it.value.sortedBy { item -> item.id })
                }.sortedBy { it.listId }
            )
    }
}

/**
 * Models Items returned by the API.
 * Separation of the object with a transformation from the pure API object
 * protects the rest of the app from changes in the API.
 */
data class GroupedItems(val listId: Int, val items: List<Item>)
