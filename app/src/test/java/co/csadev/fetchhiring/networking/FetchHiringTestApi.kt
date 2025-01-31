package co.csadev.fetchhiring.networking

import co.csadev.fetchhiring.data.Item

private val testItems = listOf(
    Item(10, 1, "Item 10"),
    Item(9, 1, "Item 9"),
    Item(8, 3, "Item 8"),
    Item(7, 3, "Item 7"),
    Item(6, 5, "Item 6"),
    Item(5, 5, "Item 5"),
    Item(4, 2, "Item 4"),
    Item(3, 2, "Item 3"),
    Item(2, 4, "Item 2"),
    Item(1, 4, "Item 1"),
)
/**
 * Implementation of [FetchHiringApi] for use in testing.
 */
class FetchHiringTestApi(val itemProvider: () -> List<Item> = { testItems }) :
    FetchHiringApi {
    override suspend fun getItemList(forceRefresh: Boolean): List<Item> {
        return itemProvider()
    }
}
