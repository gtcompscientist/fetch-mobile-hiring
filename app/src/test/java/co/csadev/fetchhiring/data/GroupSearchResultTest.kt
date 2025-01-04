package co.csadev.fetchhiring.data

import co.csadev.fetchhiring.networking.FetchHiringTestApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.junit.Test

class GroupSearchResultTest {
    private val testApi = FetchHiringTestApi()

    @Test
    fun `test results are all returned`() {
        val rawItems = testApi.itemProvider()
        assert(rawItems.size == 10)

        val results = CoroutineScope(Dispatchers.Default).async {
            val apiResult = testApi.getItemList()
            assert(apiResult.size == 10)
            apiResult.forEach {
                assert(rawItems.contains(it))
            }
        }
        while (true) {
            if (results.isCompleted) break
            Thread.sleep(100)
        }
    }

    @Test
    fun `empty results are valid`() {
        val results = CoroutineScope(Dispatchers.Default).async {
            val apiResult = FetchHiringTestApi { emptyList() }.getItemList()
            assert(apiResult.isEmpty())
        }
        while (true) {
            if (results.isCompleted) break
            Thread.sleep(100)
        }
    }
}