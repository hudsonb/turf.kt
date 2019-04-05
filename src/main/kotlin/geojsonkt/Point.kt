package geojsonkt

data class Point(val coordinates: Position, override val bbox: BBox? = null) : Geometry {
    companion object;

    constructor(longitude: Double, latitude: Double, elevation: Double = 0.0) : this(Position(longitude, latitude, elevation))

    override val type = "Point"
}

inline val Point.coordinate get() = coordinates
inline val Point.latitude get() = coordinate.latitude
inline val Point.longitude get() = coordinate.longitude
inline val Point.elevation get() = coordinate.elevation
inline val Point.y get() = coordinate.latitude
inline val Point.x get() = coordinate.longitude
inline val Point.z get() = coordinate.elevation

/**
 * Wraps this [Point] in a [Feature] with the given properties (optional).
 *
 * @return A [Feature] wrapping this [Point].
 */
fun Point.toFeature(properties: MutableMap<String, Any> = mutableMapOf()): Feature<Point> =
        Feature(this, properties)
