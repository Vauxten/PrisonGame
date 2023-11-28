package prisongame.prisongame.nbt.tag

data class CompoundTag(
    override val name: String,
    override val value: Map<String, Tag<*>>
) : Tag<Map<String, Tag<*>>>(TagType.COMPOUND, name, value)
