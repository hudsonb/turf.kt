package geojsonkt

data class LineString(val coordinates: Array<Position>, override val bbox: BBox? = null) : Geometry {
    override val type = "LineString"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineString

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
 * The number of positions in this [LineString].
 */
val LineString.size: Int get() = coordinates.size

/**
 * Returns the range of valid indices for this [LineString].
 */
val LineString.indices: IntRange get() = coordinates.indices

operator fun LineString.get(i: Int): Position = coordinates[i]

operator fun LineString.iterator(): Iterator<Position> = coordinates.iterator()