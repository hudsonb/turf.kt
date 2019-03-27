package org.turfkt

import geojsonkt.*
import java.lang.Math.toRadians
import kotlin.math.*

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * [to] The destination point.
 *
 * [units] The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 */
fun Position.distance(to: Position, units: String = "kilometers"): Double {
    val dlat = toRadians(to.latitude - latitude)
    val dlon = toRadians(to.longitude - longitude)
    val fromLat = toRadians(latitude)
    val toLat = toRadians(to.latitude)

    val a = sin(dlat / 2).pow(2) *
            sin(dlon / 2).pow(2) * cos(fromLat) * cos(toLat)

    return radiansToLength(2 * atan2(sqrt(a), sqrt(1 - a)), units)
}

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Position.distance(to: Point, units: String = "kilometers") =
        distance(to.coordinate, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Position.distance(to: Feature<Point>, units: String = "kilometers") =
        distance(to.geometry.coordinate, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Point.distance(to: Position, units: String = "kilometers"): Double =
        coordinate.distance(to, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Point.distance(to: Point, units: String = "kilometers"): Double =
        coordinate.distance(to.coordinate, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Point.distance(to: Feature<Point>, units: String = "kilometers"): Double =
        coordinate.distance(to.geometry.coordinate, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.distance(to: Position, units: String = "kilometers") =
        geometry.distance(to, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.distance(to: Point, units: String = "kilometers") =
        geometry.distance(to, units)

/**
 * Calculates the distance between two points in degrees, radians, miles, or kilometers. The Haversine formula is used
 * to account for global curvature.
 *
 * @param to The destination point.
 * @param units The units in which the distance is returned. Can be degrees, radians, miles or kilometers. Default is kilometers.
 *
 * @return The distance between two points in the given units.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.distance(to: Feature<Point>, units: String = "kilometers") =
        geometry.distance(to, units)
