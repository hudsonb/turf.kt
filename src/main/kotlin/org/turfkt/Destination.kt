package org.turfkt

import geojsonkt.*
import kotlin.math.asin
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun Position.destination(distance: Double, bearing: Double, units: String): Position {
    val rlon = longitude.toRadians()
    val rlat = latitude.toRadians()
    val rbearing = bearing.toRadians()
    val rdistance = lengthToRadians(distance)

    val lat = asin(sin(rlat) * cos(rdistance) + cos(rlat) * sin(rdistance) * cos(rbearing))
    val lon = rlon + atan2(sin(rbearing) * sin(rdistance) * cos(rlat),
            cos(rdistance) - sin(rlat) * sin(lat))

    return Position(lon.toDegrees(), lat.toDegrees(), elevation)
}

@Suppress("NOTHING_TO_INLINE")
inline fun Point.destination(distance: Double, bearing: Double, units: String): Position =
        coordinate.destination(distance, bearing, units)

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.destination(distance: Double, bearing: Double, units: String) =
        geometry.destination(distance, bearing, units)