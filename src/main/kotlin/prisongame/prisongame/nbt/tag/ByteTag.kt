package prisongame.prisongame.nbt.tag

data class ByteTag(
    override val name: String,
    override val value: Byte
) : Tag<Byte>(TagType.BYTE, name, value)