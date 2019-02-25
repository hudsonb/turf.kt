package org.turfkt

import java.lang.Math.toRadians
import kotlin.math.*

fun Position.distance(to: Position): Double {
    val dlat = toRadians(to.latitude - latitude)
    val dlon = toRadians(to.longitude - longitude)
    val fromLat = toRadians(latitude)
    val toLat = toRadians(to.latitude)

    val a = sin(dlat / 2).pow(2) *
            sin(dlon / 2).pow(2) * cos(fromLat) * cos(toLat)

    return radiansToLength(2 * atan2(sqrt(a), sqrt(1 - a)))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Position.distance(to: Point) = distance(to.coordinate)

@Suppress("NOTHING_TO_INLINE")
inline fun Point.distance(to: Position): Double = coordinate.distance(to)

@Suppress("NOTHING_TO_INLINE")
inline fun Point.distance(to: Point): Double = coordinate.distance(to.coordinate)

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.distance(to: Position) = geometry.distance(to)
