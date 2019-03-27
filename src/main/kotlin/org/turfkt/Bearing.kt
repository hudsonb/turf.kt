package org.turfkt

import geojsonkt.*
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Suppress("NOTHING_TO_INLINE")
inline fun Position.bearing(to: Position, final: Boolean = false): Double =
        calculateBearing(this, to, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Position.bearing(to: Point, final: Boolean = false): Double =
        calculateBearing(this, to.coordinate, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Position.bearing(to: Feature<Point>, final: Boolean = false): Double =
        calculateBearing(this, to.geometry.coordinate, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Point.bearing(to: Position, final: Boolean = false ): Double =
        calculateBearing(this.coordinate, to, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Point.bearing(to: Point, final: Boolean = false): Double =
        calculateBearing(this.coordinate, to.coordinate, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Point.bearing(to: Feature<Point>, final: Boolean = false): Double =
        calculateBearing(this.coordinate, to.geometry.coordinate, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.bearing(to: Position, final: Boolean = false): Double =
        calculateBearing(this.geometry.coordinate, to, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.bearing(to: Point, final: Boolean = false): Double =
        calculateBearing(this.geometry.coordinate, to.coordinate, final)

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<Point>.bearing(to: Feature<Point>, final: Boolean = false): Double =
        calculateBearing(this.geometry.coordinate, to.geometry.coordinate, final)

@PublishedApi
internal fun calculateBearing(from: Position, to: Position, final: Boolean = false): Double {
    if(final) return reversedBearing(from, to)

    val lon1 = from.longitude.toRadians()
    val lon2 = to.longitude.toRadians()
    val lat1 = from.latitude.toRadians()
    val lat2 = to.latitude.toRadians()
    val a = sin(lon2 - lon1) * cos(lon2 - lon1)
    val b = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(lon2 - lon1)

    return atan2(a, b).toDegrees()
}

@Suppress("NOTHING_TO_INLINE")
private inline fun reversedBearing(from: Position, to: Position): Double =
        (calculateBearing(to, from, false) + 180) % 360