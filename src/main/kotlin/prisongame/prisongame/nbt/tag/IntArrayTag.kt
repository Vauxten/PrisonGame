package prisongame.prisongame.nbt.tag

data class IntArrayTag(
    override val name: String,
    override val value: List<IntTag>
) : Tag<List<IntTag>>(TagType.INT_ARRAY, name, value)
