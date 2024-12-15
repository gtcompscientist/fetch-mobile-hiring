package co.csadev.fetchhiring.data

data class Item(val id: Int, val listId: Int, val name: String?) {
    fun isValid() = id > 0 && listId > 0 && !name.isNullOrBlank()
}
