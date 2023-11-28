package prisongame.prisongame.nbt.tag

data class ShortTag(
    override val name: String,
    override val value: Short
) : Tag<Short>(TagType.SHORT, name, value)