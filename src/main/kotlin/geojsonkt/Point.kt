package geojsonkt

data class Point(val coordinates: Position) : Geometry {
    constructor(longitude: Double, latitude: Double, elevation: Double = 0.0) : this(Position(longitude, latitude, elevation))

    override val type = "Point"
}

inline val Point.coordinate get() = coordinates
inline val Point.latitude get() = coordinate.latitude
inline val Point.longitude get() = coordinate.longitude
inline val Point.elevation get() = coordinate.elevation
