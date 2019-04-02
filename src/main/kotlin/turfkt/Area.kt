package turfkt

import geojsonkt.*

import kotlin.math.abs
import kotlin.math.sin


/**
 * Returns the area of the geometry in square meters.
 */
fun Geometry.area(): Double =  when(this) {
    is Polygon -> polygonArea(coordinates)
    is MultiPolygon -> coordinates.sumByDouble { polygonArea(it) }

    is Point,
    is MultiPoint,
    is LineString,
    is MultiLineString -> 0.0

    else -> throw UnsupportedOperationException("Can not calculate area of unrecognized Geometry type: ${this::class.java.name}")
}

/**
 * Returns the area of the feature's geometry in square meters.
 */
fun Feature<*>.area(): Double = geometry.area()

/**
 * Returns the total area of all feature's geometry.
 */
fun FeatureCollection.area(): Double = features.sumByDouble { it.area() }

private fun polygonArea(coordinates: Array<Array<Position>>): Double {
    var total = 0.0
    if(coordinates.isNotEmpty()) {
        total += abs(ringArea(coordinates[0]))
        for (i in 1 until coordinates.size) {
            total -= abs(ringArea(coordinates[i]))
        }
    }

    return total
}

private fun ringArea(coords: Array<Position>): Double {
    var total = 0.0
    if(coords.size > 2) {
        var p1: Position
        var p2: Position
        var p3: Position
        var lowerIndex: Int
        var middleIndex: Int
        var upperIndex: Int
        for(i in coords.indices) {
            when(i) {
                coords.size - 2 -> { // i = N - 2
                    lowerIndex = coords.size - 2
                    middleIndex = coords.size - 1
                    upperIndex = 0
                }
                coords.size - 1 -> { // i = N - 1
                    lowerIndex = coords.size - 1
                    middleIndex = 0
                    upperIndex = 1
                }
                else -> { // i = 0 to N - 3
                    lowerIndex = i
                    middleIndex = i + 1
                    upperIndex = i + 2
                }
            }
            p1 = coords[lowerIndex]
            p2 = coords[middleIndex]
            p3 = coords[upperIndex]
            total += (p3[0].toRadians() - p1[0].toRadians()) * sin(p2[1].toRadians())
        }

        total *= (EARTH_RADIUS * EARTH_RADIUS / 2)
    }

    return total
}