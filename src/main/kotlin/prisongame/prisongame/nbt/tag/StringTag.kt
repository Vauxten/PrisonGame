package prisongame.prisongame.nbt.tag

data class StringTag(
    override val name: String,
    override val value: String
) : Tag<String>(TagType.STRING, name, value)
