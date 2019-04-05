package geojsonkt

data class MultiPolygon(val coordinates: Array<Array<Array<Position>>>, override val bbox: BBox? = null) : Geometry {
    companion object;

    override val type = "MultiPolygon"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiPolygon

        if (!coordinates.contentDeepEquals(other.coordinates)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentDeepHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

/**
 * The number of polygons contained in this [MultiPolygon].
 */
val MultiPolygon.size: Int get() = coordinates.size

/**
 * The range of valid indices for this [MultiPolygon].
 */
val MultiPolygon.indices: IntRange get() = coordinates.indices

/**
 * Wraps this [MultiPolygon] in a [Feature] with the given properties (optional).
 *
 * @return A [Feature] wrapping this [MultiPolygon].
 */
fun MultiPolygon.toFeature(properties: MutableMap<String, Any> = mutableMapOf()): Feature<MultiPolygon> =
        Feature(this, properties)

operator fun MultiPolygon.get(i: Int) = coordinates[i]