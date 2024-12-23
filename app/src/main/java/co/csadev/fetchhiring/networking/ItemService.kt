package co.csadev.fetchhiring.networking

import co.csadev.fetchhiring.data.Item
import retrofit2.http.GET

interface ItemService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}
