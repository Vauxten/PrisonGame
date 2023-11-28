package prisongame.prisongame.nbt.tag

data class LongTag(
    override val name: String,
    override val value: Long
) : Tag<Long>(TagType.LONG, name, value)
