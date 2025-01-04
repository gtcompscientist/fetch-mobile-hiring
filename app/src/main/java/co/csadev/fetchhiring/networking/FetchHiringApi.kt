package co.csadev.fetchhiring.networking

import co.csadev.fetchhiring.data.Item

/**
 * Interface to the backend API, this is done for testing purposes.
 */
interface FetchHiringApi {
    /**
     * Fetches the JSON [Item] data from the Instructions
     */
    suspend fun getItemList(forceRefresh: Boolean = false): List<Item>
}