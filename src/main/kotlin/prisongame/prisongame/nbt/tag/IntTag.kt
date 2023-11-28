package prisongame.prisongame.nbt.tag

data class IntTag(
    override val name: String,
    override val value: Int
) : Tag<Int>(TagType.INT, name, value)