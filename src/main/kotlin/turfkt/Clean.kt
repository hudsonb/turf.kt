package turfkt

import geojsonkt.*

/**
 * Returns a new [MultiPoint] with redundant coordinates removed.
 */
fun MultiPoint.clean(): MultiPoint =
        MultiPoint(coordinates.clean())

/**
 * Returns a new [LineString] with redundant coordinates removed.
 */
fun LineString.clean(): LineString =
        LineString(coordinates.clean())

/**
 * Returns a new [MultiLineString] with redundant coordinates removed.
 */
fun MultiLineString.clean(): MultiLineString =
        MultiLineString(Array(coordinates.size) { i -> coordinates[i].clean() })

/**
 * Returns a new [Polygon] with redundant coordinates removed.
 */
fun Polygon.clean(): Polygon =
        Polygon(Array(coordinates.size) { i -> coordinates[i].clean() })

/**
 * Returns a new [MultiPolygon] with redundant coordinates removed.
 */
fun MultiPolygon.clean(): MultiPolygon =
        MultiPolygon(Array(coordinates.size) { i -> Array(coordinates[i].size) { j -> coordinates[i][j].clean() } })

/**
 * Returns a new [Feature] wrapping a new geometry with redundant coordinates removed.
 */
fun Feature<*>.clean(): Feature<*> {
    return when(geometry) {
        is MultiPoint -> Feature(geometry.clean())
        is LineString -> Feature(geometry.clean())
        is MultiLineString -> Feature(geometry.clean())
        is Polygon -> Feature(geometry.clean())
        is MultiPolygon -> Feature(geometry.clean())
        is Point -> Feature(Point(geometry.coordinate))
        else -> this
    }
}

internal fun Array<Position>.clean(): Array<Position> {
    if(isEmpty()) return this

    var previous = first()
    val clean = mutableListOf(first())

    for(i in 1 until size) {
        if(previous != this[i]) clean += this[i]
        previous = this[i]
    }

    return clean.toTypedArray()
}