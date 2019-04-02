package org.turfkt

import geojsonkt.Geometry
import geojsonkt.Feature
import geojsonkt.FeatureCollection
import geojsonkt.Polygon
import geojsonkt.MultiPolygon
import geojsonkt.Point
import geojsonkt.MultiPoint
import geojsonkt.LineString
import geojsonkt.MultiLineString
import geojsonkt.Position

import kotlin.math.sin
import kotlin.math.abs

private fun calculateArea( geom: Geometry): Double{
    var total = 0.0
    when (geom) {
        is Polygon -> { 
            return polygonArea(geom.coordinates)
        }
        is MultiPolygon -> {
            for (i in 0 until geom.coordinates.size) {
                total += polygonArea(geom.coordinates[i])
            }
            return total
        }
        is Point -> return 0.0
        is MultiPoint -> return 0.0
        is LineString -> return 0.0
        is MultiLineString -> return 0.0
        else -> throw UnsupportedOperationException("Can not calculate area of unrecognized Geometry type: ${geom::class.java.name}")
    }
}

private fun polygonArea(coordinates: Array<Array<Position>>): Double {
    var total = 0.0
    if (coordinates.isNotEmpty()) {
        total += abs(ringArea(coordinates[0]))
        for (i in 1 until coordinates.size){
            total -= abs(ringArea(coordinates[i]))
        }
    }
    return total
}

private fun ringArea(coords: Array<Position>): Double {
    var p1: Position
    var p2: Position
    var p3: Position
    var lowerIndex: Int
    var middleIndex: Int
    var upperIndex: Int
    var total = 0.0

    val coordinateLength = coords.size
    if (coordinateLength > 2) {
        for (i in 0 until coordinateLength) {
            if (i == coordinateLength - 2) { // i = N - 2
                lowerIndex = coordinateLength - 2
                middleIndex = coordinateLength - 1
                upperIndex = 0
            }
            else if (i == coordinateLength - 1) { // i = N - 1
                lowerIndex = coordinateLength - 1
                middleIndex = 0
                upperIndex = 1
            }
            else { // i = 0 to N - 3
                lowerIndex = i 
                middleIndex = i + 1
                upperIndex = i + 2
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
fun Geometry.area(): Double = calculateArea(this)
fun Feature<*>.area(): Double {
    return when (geometry) {
        is Polygon -> geometry.area()
        is MultiPolygon -> geometry.area()
        is Point -> return 0.0
        is MultiPoint -> return 0.0
        is LineString -> return 0.0
        is MultiLineString -> return 0.0
        else -> throw UnsupportedOperationException("Can not calculate area of unrecognized Geometry type: ${this::class.java.name}")
        }
    }

fun FeatureCollection.area(): Double {
    var total = 0.0
    for (feature in features) {
        total += feature.area()
    }
    return total
}