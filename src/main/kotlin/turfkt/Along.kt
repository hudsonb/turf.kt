package turfkt

import geojsonkt.Feature
import geojsonkt.LineString
import geojsonkt.Position

/**
 * Returns a [Position] at a specified distance along this [LineString].
 *
 * @param distance The distance along the [LineString]
 * @param units The units in which the [distance] is provided.
 */
fun LineString.along(distance: Double, units: String): Position = coordinates.along(distance, units)

/**
 * Returns a [Position] at a specified distance along this [LineString].
 *
 * @param distance The distance along the [LineString]
 * @param units The units in which the [distance] is provided.
 */
fun Feature<LineString>.along(distance: Double, units: String): Position =
        geometry.along(distance, units)

/**
 * Returns a [Position] at a specified distance along the line represented by this array.
 *
 * @param distance The distance along the line.
 * @param units The units in which the [distance] is provided.
 */
fun Array<Position>.along(distance: Double, units: String): Position {
    var travelled = 0.0
    for(i in indices) {
        if(distance >= travelled && i == lastIndex) break

        if(distance == travelled) return get(i)

        if(travelled > distance) {
            val overshot = distance - travelled
            val direction = get(i).bearing(get(i - 1)) - 180
            val interpolated = get(i).destination(overshot, direction, units)
            return interpolated
        }

        travelled += get(i).distance(get(i + 1))
    }

    return last()
}