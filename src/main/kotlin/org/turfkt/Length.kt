package org.turfkt

import geojsonkt.*

/**
 * Calculates the length of the feature/geometry in the specified units.
 *
 * Length is calculated as follows:
 * - [Point] 0.0
 * - [MultiPoint] 0.0
 * - [LineString] length of the line string
 * - [MultiLineString] combined length of all line strings
 * - [Polygon] length of the exterior hull
 * - [MultiPolygon] combined length of all polygons
 * - [Feature] length of the wrapped geometry
 *
 * @param units The units of measure to calculate the distance in. Defaults to kilometers.
 */
fun Geometry.length(units: String = "kilometers"): Double {
    return when(this) {
        is Point -> 0.0
        is MultiPoint -> 0.0
        is LineString -> coordinates.length(units)
        is MultiLineString -> coordinates.map { it.length(units) }.sum()
        is Polygon -> coordinates.first().length(units)
        is MultiPolygon -> coordinates.map { it.first().length(units) }.sum()
        is Feature<*> -> geometry.length(units)
        else -> throw UnsupportedOperationException("Can not calculate length of unrecognized Geometry type: ${this::class.java.name}")
    }
}

private fun Array<Position>.length(units: String = "kilometers"): Double {
    var previousPosition: Position? = null
    return fold(0.0) { acc, currentPosition ->
        val d = acc + (previousPosition?.distance(currentPosition, units) ?: 0.0)
        previousPosition = currentPosition
        d
    }
}