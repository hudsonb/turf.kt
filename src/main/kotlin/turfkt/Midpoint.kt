package turfkt

import geojsonkt.*

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Position.midpoint(to: Position): Position {
    val dist = distance(to)
    val heading = bearing(to)

    return destination(dist / 2, heading)
}

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Position.midpoint(to: Point): Position = midpoint(to.coordinate)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Position.midpoint(to: Feature<Point>): Position = midpoint(to.geometry.coordinate)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Point.midpoint(to: Position): Position = coordinate.midpoint(to)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Point.midpoint(to: Point): Position = coordinate.midpoint(to.coordinate)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Point.midpoint(to: Feature<Point>): Position = coordinate.midpoint(to.geometry.coordinate)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Feature<Point>.midpoint(to: Position): Position = geometry.coordinate.midpoint(to)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Feature<Point>.midpoint(to: Point): Position = geometry.coordinate.midpoint(to.coordinate)

/**
 * Returns a position midway between the given points. The midpoint is calculated geodiscally, meaning the curvature of
 * the earth is taken into account.
 *
 * @param to The end point
 * @returns The midpoint
 */
fun Feature<Point>.midpoint(to: Feature<Point>): Position = geometry.coordinate.midpoint(to.geometry.coordinate)