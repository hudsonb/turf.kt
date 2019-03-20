package geojsonkt

data class LineString(val coordinates: Array<Position>) : Geometry {
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

operator fun LineString.get(i: Int) = coordinates[i]

operator fun LineString.iterator() = coordinates.iterator()