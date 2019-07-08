package turfkt

import geojsonkt.*

fun Position.midpoint(to: Position): Position {
    val dist = distance(to)
    val heading = bearing(to)

    return destination(dist / 2, heading)
}

fun Position.midpoint(to: Point): Position = midpoint(to.coordinate)

fun Position.midpoint(to: Feature<Point>): Position = midpoint(to.geometry.coordinate)

fun Point.midpoint(to: Position): Position = coordinate.midpoint(to)

fun Point.midpoint(to: Point): Position = coordinate.midpoint(to.coordinate)

fun Point.midpoint(to: Feature<Point>): Position = coordinate.midpoint(to.geometry.coordinate)

fun Feature<Point>.midpoint(to: Position): Position = geometry.coordinate.midpoint(to)

fun Feature<Point>.midpoint(to: Point): Position = geometry.coordinate.midpoint(to.coordinate)

fun Feature<Point>.midpoint(to: Feature<Point>): Position = geometry.coordinate.midpoint(to.geometry.coordinate)