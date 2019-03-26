package org.turfkt

import geojsonkt.*
import java.lang.UnsupportedOperationException

/**
 * Returns a new [Point] with the x and y components flipped.
 */
fun Point.flip(): Point = Point(coordinate.flip())

/**
 * Returns a new [LineString] with the x and y components of each coordinate flipped.
 */
fun LineString.flip(): LineString = LineString(coordinates.flip())

/**
 * Returns a new [Polygon] with the x and y components of all coordinates flipped.
 */
fun Polygon.flip(): Polygon = Polygon(coordinates.flip())

/**
 * Returns a new [MultiPoint] with the x and y components of all coordinates flipped.
 */
fun MultiPoint.flip(): MultiPoint = MultiPoint(coordinates.flip())

/**
 * Returns a new [MultiLineString] with the x and y components of all coordinates flipped.
 */
fun MultiLineString.flip(): MultiLineString = MultiLineString(coordinates.flip())

/**
 * Returns a new [MultiPolygon] with the x and y components of all coordinates flipped.
 */
fun MultiPolygon.flip(): MultiPolygon = MultiPolygon(coordinates.flip())

/**
 * Returns a new [Polygon] with the x and y components flipped.
 */
fun Position.flip(): Position = Position(latitude, longitude, elevation)

/**
 * Returns a new [Feature] with the coordinates of the wrapped geometry flipped.
 */
fun Feature<*>.flip(): Feature<*> {
    return when(geometry) {
        is Point -> copy(geometry = geometry.flip())
        is LineString -> copy(geometry = geometry.flip())
        is Polygon -> copy(geometry = geometry.flip())
        is MultiPoint -> copy(geometry = geometry.flip())
        is MultiLineString -> copy(geometry = geometry.flip())
        is MultiPolygon -> copy(geometry = geometry.flip())
        else -> throw UnsupportedOperationException("Unrecognized geometry type.")
    }
}

private fun Array<Position>.flip(): Array<Position> = Array(size) { this[it] }

private fun Array<Array<Position>>.flip(): Array<Array<Position>> = Array(size) { this[it].flip() }

private fun Array<Array<Array<Position>>>.flip(): Array<Array<Array<Position>>> = Array(size) { this[it].flip() }
