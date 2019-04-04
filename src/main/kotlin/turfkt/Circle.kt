package turfkt

import geojsonkt.*

/**
 * Generates a [Polygon] approximating a circle centered at the given position with the given radius.
 *
 * @param center The center of the circle.
 * @param radius The radius of the circle.
 * @param units The units for radius.
 * @param steps The number of points in the polygon.
 *
 * @return A [Polygon] approximating a circle.
 */
fun circle(center: Position, radius: Double, units: String = "kilometers", steps: Int = 64): Polygon {
    val coords = ArrayList<Position>(steps)

    repeat(steps) { i ->
        center.destination(radius, i * -360.0 / steps, units)
    }

    coords += coords.first()

    return Polygon(arrayOf(coords.toTypedArray()))
}

/**
 * Generates a [Polygon] approximating a circle centered at the given position with the given radius.
 *
 * @param center The center of the circle.
 * @param radius The radius of the circle.
 * @param units The units for radius.
 * @param steps The number of points in the polygon.
 *
 * @return A [Polygon] approximating a circle.
 */
fun circle(center: Point, radius: Double, units: String = "kilometers", steps: Int = 64): Polygon =
        circle(center.coordinate, radius, units, steps)

/**
 * Generates a [Polygon] approximating a circle centered at the given position with the given radius.
 *
 * @param center The center of the circle.
 * @param radius The radius of the circle.
 * @param units The units for radius.
 * @param steps The number of points in the polygon.
 *
 * @return A [Polygon] approximating a circle.
 */
fun circle(center: Feature<Point>, radius: Double, units: String = "kilometers", steps: Int = 64): Polygon =
        circle(center.geometry.coordinate, radius, units, steps)

