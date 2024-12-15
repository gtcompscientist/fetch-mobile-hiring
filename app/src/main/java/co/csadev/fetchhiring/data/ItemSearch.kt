package co.csadev.fetchhiring.data

data class ItemSearchState(val listGroups: Set<ItemGroup>)

data class ItemGroup(val items: Set<Item>)
