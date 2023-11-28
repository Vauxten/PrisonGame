package prisongame.prisongame.nbt

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import prisongame.prisongame.nbt.tag.CompoundTag
import prisongame.prisongame.nbt.tag.Tag

class OfflinePlayerPDC(val bukkitValues: CompoundTag) : PersistentDataContainer {
    val adapterContext = OfflinePlayerPersistentDataAdapterContext()

    override fun <T : Any?, Z : Any?> set(p0: NamespacedKey, p1: PersistentDataType<T, Z>, p2: Z & Any) {
        throw UnsupportedOperationException("Can't write to offline players")
    }

    override fun <T : Any?, Z : Any?> has(p0: NamespacedKey, p1: PersistentDataType<T, Z>): Boolean {
        val key = "${p0.namespace}:${p0.key}"
        return has(p0) && bukkitValues.value[key]!!.value!!.javaClass == p1.complexType
    }

    override fun has(p0: NamespacedKey): Boolean {
        val key = "${p0.namespace}:${p0.key}"
        return key in bukkitValues.value
    }

    override fun <T : Any?, Z : Any?> get(p0: NamespacedKey, p1: PersistentDataType<T, Z>): Z? {
        val key = "${p0.namespace}:${p0.key}"

        @Suppress("UNCHECKED_CAST")
        val tag = (bukkitValues.value[key] ?: return null) as Tag<T & Any>

        return p1.fromPrimitive(tag.value, adapterContext)
    }

    override fun <T : Any?, Z : Any?> getOrDefault(
        p0: NamespacedKey,
        p1: PersistentDataType<T, Z>,
        p2: Z & Any
    ): Z & Any {
        return get(p0, p1) ?: p2
    }

    override fun getKeys(): MutableSet<NamespacedKey> {
        val keys = mutableSetOf<NamespacedKey>()

        for (key in bukkitValues.value.keys) {
            val chunks = key.split(":")
            keys.add(NamespacedKey(chunks[0], chunks[1]))
        }

        return keys
    }

    override fun remove(p0: NamespacedKey) {
        throw UnsupportedOperationException("Can't write to offline players")
    }

    override fun isEmpty(): Boolean {
        return bukkitValues.value.isEmpty()
    }

    override fun getAdapterContext(): PersistentDataAdapterContext {
        return adapterContext
    }

    override fun serializeToBytes(): ByteArray {
        throw UnsupportedOperationException("Can't write to offline players")
    }

    override fun readFromBytes(p0: ByteArray, p1: Boolean) {
        throw UnsupportedOperationException("Not needed")
    }
}