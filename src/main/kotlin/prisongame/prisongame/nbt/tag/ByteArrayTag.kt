package prisongame.prisongame.nbt.tag

data class ByteArrayTag(
    override val name: String,
    override val value: List<ByteTag>
) : Tag<List<ByteTag>>(TagType.BYTE_ARRAY, name, value)
