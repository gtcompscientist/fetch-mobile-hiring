package co.csadev.fetchhiring.networking

import android.util.Log
import co.csadev.fetchhiring.data.Item
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val FETCH_HIRING_BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

/**
 * Implementation of [FetchHiringApi] using [ItemService] to perform the API requests.
 */
class FetchHiringApiImpl : FetchHiringApi {
    private val service: ItemService

    companion object {
        private const val TAG = "FETCH_API"
        private const val LIVE_RETRIEVAL_DELAY = 5 * 60 * 1000L // 5 minutes
        private var lastRetrievalTime = Long.MIN_VALUE
        private var lastRetrievedItems: List<Item>? = null
    }

    init {
        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        service = Retrofit.Builder()
            .baseUrl(FETCH_HIRING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ItemService::class.java)
    }

    override suspend fun getItemList(forceRefresh: Boolean): List<Item> {
        val now = System.currentTimeMillis()

        // Check if we need to refresh the data
        val shouldRefresh = forceRefresh ||
                (now - lastRetrievalTime > LIVE_RETRIEVAL_DELAY) ||
                lastRetrievedItems == null

        if (!shouldRefresh) {
            // Return cached items if refresh is not needed
            return lastRetrievedItems ?: emptyList()
        }

        // Refresh logic
        lastRetrievalTime = now
        Log.d(TAG, "Fetching items from server @ $now")

        return try {
            // Fetch new data
            service.getItems()
        } catch (e: Exception) {
            // Log the error and reset relevant states
            Log.e(TAG, "Error fetching items", e)
            lastRetrievalTime = now - LIVE_RETRIEVAL_DELAY // Allow retry immediately
            emptyList() // Return an empty list to signify failure
        }.also { lastRetrievedItems = it }
    }
}
