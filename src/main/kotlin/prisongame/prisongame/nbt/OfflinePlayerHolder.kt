package prisongame.prisongame.nbt

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataHolder
import prisongame.prisongame.nbt.tag.CompoundTag
import java.io.FileInputStream
import java.util.zip.GZIPInputStream

class OfflinePlayerHolder(val player: OfflinePlayer) : PersistentDataHolder {
    private val world = Bukkit.getWorlds()[0]
    private val file = world.worldFolder.resolve("playerdata/${player.uniqueId}.dat")
    private var reader: NBTReader? = null
    private var compound: CompoundTag
    private var values: CompoundTag

    init {
        val empty = CompoundTag("BukkitValues", emptyMap())

        if (!file.exists()) {
            compound = CompoundTag("", emptyMap())
            values = empty
        } else {
            reader = NBTReader(GZIPInputStream(FileInputStream(file)))
            compound = reader!!.readTag() as CompoundTag
            values = (compound.value["BukkitValues"] ?: empty) as CompoundTag
            reader!!.stream.close()
        }
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        return OfflinePlayerPDC(values)
    }
}