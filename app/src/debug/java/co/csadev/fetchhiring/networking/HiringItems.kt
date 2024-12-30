package co.csadev.fetchhiring.networking

import android.content.Context
import co.csadev.fetchhiring.data.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object HiringItems {
    fun getItemsFromAssets(context: Context): List<Item>? {
        return try {
            val inputStream = context.assets.open("hiring.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonString = String(buffer, Charsets.UTF_8)
            Gson().fromJson<List<Item>>(jsonString, object : TypeToken<List<Item>>() {}.type).toList()
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}
fun readHiringJson(context: Context): List<Item>? {
    return try {
        val inputStream = context.assets.open("hiring.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = String(buffer, Charsets.UTF_8)
        Gson().fromJson<List<Item>>(jsonString, object : TypeToken<List<Item>>() {}.type).toList()
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}