package prisongame.prisongame.nbt.tag

data class FloatTag(
    override val name: String,
    override val value: Float
) : Tag<Float>(TagType.FLOAT, name, value)
