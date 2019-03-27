package org.turfkt

import geojsonkt.Feature
import geojsonkt.LineString
import geojsonkt.Position

/**
 * Returns true if the LineString's coordinates are in clockwise order.
 */
fun LineString.isClockwise(): Boolean = coordinates.isClockwise()

/**
 * Returns true if the LineString's coordinates are in counter-clockwise order.
 */
fun LineString.isCounterClockwise(): Boolean = coordinates.isCounterClockwise()

/**
 * Returns true if the Feature's underlying LineString's coordinates are in clockwise order.
 */
fun Feature<LineString>.isClockwise(): Boolean = geometry.isClockwise()

/**
 * Returns true if the Feature's underlying LineString's coordinates are in counter-clockwise order.
 */
fun Feature<LineString>.isCounterClockwise(): Boolean = geometry.isCounterClockwise()

/**
 * Returns true if the coordinates in this linear ring are in clockwise order.
 */
fun Array<Position>.isCounterClockwise(): Boolean = !isClockwise()

/**
 * Returns true if the coordinates in this linear ring are in counter-clockwise order.
 */
fun Array<Position>.isClockwise(): Boolean {
    var sum = 0.0
    var previous: Position
    var current: Position
    for(i in 1 until size) {
        previous = this[i - 1]
        current = this[i]
        sum += ((current[0] - previous[0]) * (current[1] * previous[1]))
    }

    return sum > 0
}