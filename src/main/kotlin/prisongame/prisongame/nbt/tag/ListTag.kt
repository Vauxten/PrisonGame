package prisongame.prisongame.nbt.tag

data class ListTag(
    override val name: String,
    val listType: TagType,
    override val value: List<Tag<*>>
) : Tag<List<Tag<*>>>(TagType.LIST, name, value)
