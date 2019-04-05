package geojsonkt

data class Polygon(val coordinates: Array<Array<Position>>, override val bbox: BBox? = null) : Geometry {
    companion object;

    override val type = "Polygon"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polygon

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
 * The number of linear rings this [Polygon] is comprised of.
 */
val Polygon.size: Int get() = coordinates.size

/**
 * The range of valid indices for the [Polygon].
 */
val Polygon.indices: IntRange get() = coordinates.indices

/**
 * The exterior ring of this [Polygon].
 */
val Polygon.exteriorRing: Array<Position> get() = coordinates[0]

/**
 * Wraps this [LineString] in a [Feature] with the given properties (optional).
 *
 * @return A [Feature] wrapping this [LineString].
 */
fun Polygon.toFeature(properties: MutableMap<String, Any> = mutableMapOf()): Feature<Polygon> =
        Feature(this, properties)

operator fun Polygon.get(i: Int): Array<Position> = coordinates[i]

operator fun Polygon.iterator(): Iterator<Array<Position>> = coordinates.iterator()