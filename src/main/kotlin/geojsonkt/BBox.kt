package geojsonkt

import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.Double.Companion.NEGATIVE_INFINITY

fun BBox(swlon: Double, swlat: Double, nelon: Double, nelat: Double) =
        BBox(doubleArrayOf(swlon, swlat, nelon, nelat))

fun BBox(swlon: Double, swlat: Double, swelevation: Double,
         nelon: Double, nelat: Double, neelevation: Double) =
        BBox(doubleArrayOf(swlon, swlat, swelevation, nelon, nelat, neelevation))

fun BBox(sw: Position, ne: Position): BBox {
    if(sw.elevation.isNaN() || ne.elevation.isNaN())
        return BBox(doubleArrayOf(sw.longitude, sw.latitude, ne.longitude, ne.latitude))

    return BBox(doubleArrayOf(sw.longitude, sw.latitude, sw.elevation, ne.longitude, ne.latitude, ne.elevation))
}

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class BBox(private val values: DoubleArray) {
    companion object

    val southWest: Position
        get() = when(values.size) {
            4 -> Position(values[0], values[1])
            6 -> Position(values[0], values[1], values[2])
            else -> throw IllegalStateException("Unexpected number of values, 4 or 6 expected but ${values.size} were found.")
        }

    val northEast: Position
        get()  = when(values.size) {
            4 -> Position(values[2], values[3])
            6 -> Position(values[3], values[4], values[5])
            else -> throw IllegalStateException("Unexpected number of values, 4 or 6 expected but ${values.size} were found.")
        }
}

/**
 * Returns a [Polygon] equivalent to this [BBox].
 */
fun BBox.toPolygon(): Polygon {
    val north = northEast.latitude
    val east = northEast.longitude
    val west = southWest.longitude
    val south = southWest.latitude
    val northWest = Position(west, north)
    val southEast = Position(east, south)

    return Polygon(arrayOf(arrayOf(northWest, southWest, southEast, northEast, northWest)))
}

fun GeoJson.bbox(): BBox {
    when(this) {
        is Point -> return bbox(coords(this))
        is LineString -> return bbox(coords(this))
        is Polygon -> return bbox(coords(this))
        is MultiPoint -> return bbox(coords(this))
        is MultiLineString -> return bbox(coords(this))
        is MultiPolygon -> return bbox(coords(this))
        is GeometryCollection -> return bbox(coords(this))
        else -> throw UnsupportedOperationException("Can not calculate BBox of unrecognized Geometry type: ${this::class.java.name}")
    }
}

private fun bbox(coords: Sequence<Position>): BBox {
    var result = doubleArrayOf(POSITIVE_INFINITY, POSITIVE_INFINITY, NEGATIVE_INFINITY, NEGATIVE_INFINITY)

    for(coord in coords) {
            if (result[0] > coord[0]) { result[0] = coord[0] }
            if (result[1] > coord[1]) { result[1] = coord[1] }
            if (result[2] < coord[0]) { result[2] = coord[0] }
            if (result[3] < coord[1]) { result[3] = coord[1] }
    }

    return BBox(result)
}

private fun coords(g: Geometry): Sequence<Position> = when(g) {
    is Point -> arrayOf(g.coordinate).asSequence()
    is LineString -> g.coordinates.asSequence()
    is Polygon -> g.coordinates.first().asSequence()
    is MultiPoint -> g.coordinates.asSequence()
    is MultiLineString -> g.coordinates.flatten().asSequence()
    is MultiPolygon -> g.coordinates.flatMap { it.first().toList() }.asSequence()
    is GeometryCollection -> g.geometries.asSequence().map { coords(it) }.flatten()
    else -> emptySequence()
}
