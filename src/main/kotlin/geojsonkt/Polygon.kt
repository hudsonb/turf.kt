package geojsonkt

data class Polygon(val coordinates: Array<Array<Position>>, override val bbox: BBox? = null) : Geometry {
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

operator fun Polygon.get(i: Int): Array<Position> = coordinates[i]

operator fun Polygon.iterator(): Iterator<Array<Position>> = coordinates.iterator()