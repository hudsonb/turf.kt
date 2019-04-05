package turfkt

import geojsonkt.*

/**
 * Creates a circular arc, of a circle of the given radius and center point, between [startBearing] and [endBearing].
 *
 * @param center: The center coordinate of the circular arc
 * @param radius: The radius of the circular arc
 * @param startBearing: The starting angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param endBearing: The ending angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param steps: The number of steps to use to generate generating the arc. Defaults to 64.
 * @param units: The units in which the radius is specified.
 *
 * @return A [LineString] approximating a circular arc.
 */
fun arc(center: Position, radius: Double, startBearing: Double, endBearing: Double, steps: Int = 64,
        units: String = "kilometers"): LineString {
    val arcStart = normalize(startBearing)
    var arcEnd = normalize(endBearing)

    if(arcStart == arcEnd) {
        val p = circle(center, radius, units, steps)
        return LineString(p.coordinates.first())
    }

    if(arcStart > arcEnd) arcEnd += 360

    var i = 0
    var alpha = arcStart
    val coordinates = ArrayList<Position>(steps)
    while(alpha < arcEnd) {
        coordinates += center.destination(radius, alpha, units)
        ++i
        alpha = arcStart + i * 360 / steps
    }

    if(alpha > arcEnd) coordinates += center.destination(radius, arcEnd, units)

    return LineString(coordinates.toTypedArray())
}

/**
 * Creates a circular arc, of a circle of the given radius and center point, between [startBearing] and [endBearing].
 *
 * @param center: The center coordinate of the circular arc
 * @param radius: The radius of the circular arc
 * @param startBearing: The starting angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param endBearing: The ending angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param steps: The number of steps to use to generate generating the arc. Defaults to 64.
 * @param units: The units in which the radius is specified.
 *
 * @return A [LineString] approximating a circular arc.
 */
fun arc(center: Point, radius: Double, startBearing: Double, endBearing: Double, steps: Int = 64,
        units: String = "kilometers"): LineString =
        arc(center.coordinate, radius, startBearing, endBearing, steps, units)

/**
 * Creates a circular arc, of a circle of the given radius and center point, between [startBearing] and [endBearing].
 *
 * @param center: The center coordinate of the circular arc
 * @param radius: The radius of the circular arc
 * @param startBearing: The starting angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param endBearing: The ending angle of the circular arc in degrees. A bearing of 0 is north of the center coordinate.
 * @param steps: The number of steps to use to generate generating the arc. Defaults to 64.
 * @param units: The units in which the radius is specified.
 *
 * @return A [LineString] approximating a circular arc.
 */
fun arc(center: Feature<Point>, radius: Double, startBearing: Double, endBearing: Double, steps: Int = 64,
        units: String = "kilometers"): LineString =
        arc(center.geometry.coordinate, radius, startBearing, endBearing, steps, units)

/**
 * Returns a valid angle between 0-360.
 *
 * @param angle An angle between -180-180 degrees
 *
 * @return An angle between 0-360 degrees.
 */
private fun normalize(angle: Double): Double {
    val beta = angle % 360
    return if(beta < 0) angle + 360 else beta
}