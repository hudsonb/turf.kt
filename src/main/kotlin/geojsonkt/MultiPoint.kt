package geojsonkt

data class MultiPoint(val coordinates: Array<Position>) : Geometry {
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
 * Returns an iterator over the positions in this [MultiPoint]. Enables [MultiPoint] to be iterated over in for loops.
 */
operator fun MultiPoint.iterator() = coordinates.iterator()

/**
 * Allows indexed based access to the positions contained in this [MultiPoint].
 */
operator fun MultiPoint.get(i: Int) = coordinates[i]

/**
 * Determines whether or not this [MultiPoint] contains a position equal to the given position.
 */
operator fun MultiPoint.contains(pos: Position) = pos in coordinates

/**
 * Determines whether or not this [MultiPoint] contains a position equal to the position of the given point.
 */
operator fun MultiPoint.contains(point: Point) = point.coordinate in coordinates

/**
 * Determines whether or not this [MultiPoint] contains a position equal to the position of the given feature's point geometry.
 */
operator fun MultiPoint.contains(feature: Feature<Point>) = feature.geometry.coordinate in coordinates

inline fun MultiPoint.forEach(action: (Position) -> Unit) = coordinates.forEach(action)

inline fun <R> MultiPoint.map(transform: (Position) -> R): List<R> = coordinates.map(transform)

inline fun <R> MultiPoint.fold(initial: R, operation: (acc: R, Position) -> R) = coordinates.fold(initial, operation)

