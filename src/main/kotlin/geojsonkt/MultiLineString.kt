package geojsonkt

data class MultiLineString(val coordinates: Array<Array<Position>>) : Geometry {
    override val type = "MultiLineString"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiLineString

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
 * The number of line strings contained within this [MultiLineString].
 */
val MultiLineString.size: Int get() = coordinates.size

/**
 * Returns the range of valid indices for the [MultiLineString].
 */
val MultiLineString.indices: IntRange get() = coordinates.indices

/**
 * Returns an iterator over the line strings in this [MultiLineString]. Enables [MultiLineString] to be iterated over in for loops.
 */
operator fun MultiLineString.iterator() = coordinates.iterator()

/**
 * Allows indexed based access to the line strings contained in this [MultiLineString].
 */
operator fun MultiLineString.get(i: Int) = coordinates[i]

/**
 * Determines whether or not this [MultiLineString] contains a line string equal to the given linestring.
 */
operator fun MultiLineString.contains(ls: Array<Position>) = ls in coordinates

/**
 * Determines whether or not this [MultiLineString] contains a line string equal to the given line string.
 */
operator fun MultiLineString.contains(ls: LineString) = ls.coordinates in coordinates

/**
 * Determines whether or not this [MultiLineString] contains a line string equal to the features line string geometry.
 */
operator fun MultiLineString.contains(feature: Feature<LineString>) = feature.geometry.coordinates in coordinates

