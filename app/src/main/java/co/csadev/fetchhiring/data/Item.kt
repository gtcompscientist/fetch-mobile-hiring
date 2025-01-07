package co.csadev.fetchhiring.data

data class Item(val id: Int, val listId: Int, val name: String?) {
    // Added here to make it testable
    val shouldDisplayName: Boolean
        get() = name.isNullOrBlank().not()
}
