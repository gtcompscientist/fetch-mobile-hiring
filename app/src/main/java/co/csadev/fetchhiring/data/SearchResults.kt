package co.csadev.fetchhiring.data


/**
 * Models Items returned by the API and used in the app in a stateful manner for reliability
 */
data class ItemState(val list: List<Item> = emptyList())
//data class ItemSearchState(val listGroups: Set<GroupSearchResult> = emptySet())

/**
 * Models Items returned by the API.
 * Separation of the object with a transformation from the pure API object
 * protects the rest of the app from changes in the API.
 */
data class GroupSearchResult(val listId: Int, val items: List<ItemSearchResult>) {
    companion object {
        fun fromFlatList(items: List<Item>) =
            items.groupBy { it.listId }
                .map {
                    GroupSearchResult(
                        it.key,
                        it.value.map {
                            item -> ItemSearchResult.fromItem(item)
                        }.sortedBy { item -> item.id })
                }.sortedBy { it.listId }
    }
}

/**
 * Models Items returned by the API.
 */
data class ItemSearchResult(val id: Int, val listId: Int, val name: String?) {
    companion object {
        fun fromItem(item: Item) = ItemSearchResult(item.id, item.listId, item.name)
    }
}
