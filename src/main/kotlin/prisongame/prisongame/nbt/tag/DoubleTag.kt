package prisongame.prisongame.nbt.tag

data class DoubleTag(
    override val name: String,
    override val value: Double
) : Tag<Double>(TagType.DOUBLE, name, value)
