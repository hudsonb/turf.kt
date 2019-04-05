package geojsonkt

data class MultiPoint(val coordinates: Array<Position>, override val bbox: BBox? = null) : Geometry {
    companion object;

    override val type = "MultiPoint"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiPoint

        if (!coordinates.contentEquals(other.coordinates)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

/**
 * The number of positions contained within this [MultiPoint].
 */
val MultiPoint.size: Int get() = coordinates.size

/**
 * Returns the range of valid indices for the [MultiPoint].
 */
val MultiPoint.indices: IntRange get() = coordinates.indices

/**
 * Wraps this [MultiPoint] in a [Feature] with the given properties (optional).
 *
 * @return A [Feature] wrapping this [MultiPoint].
 */
fun MultiPoint.toFeature(properties: MutableMap<String, Any> = mutableMapOf()): Feature<MultiPoint> =
        Feature(this, properties)

/**
 * Returns an iterator over the positions in this [MultiPoint]. Enables [MultiPoint] to be iterated over in for loops.
 */
operator fun MultiPoint.iterator(): Iterator<Position> = coordinates.iterator()

/**
 * Allows indexed based access to the positions contained in this [MultiPoint].
 */
operator fun MultiPoint.get(i: Int): Position = coordinates[i]