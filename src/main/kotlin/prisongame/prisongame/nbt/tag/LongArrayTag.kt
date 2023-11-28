package prisongame.prisongame.nbt.tag

data class LongArrayTag(
    override val name: String,
    override val value: List<LongTag>
) : Tag<List<LongTag>>(TagType.LONG_ARRAY, name, value)
