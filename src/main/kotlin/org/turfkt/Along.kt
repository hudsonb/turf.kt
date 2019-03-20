package org.turfkt

import geojsonkt.Feature
import geojsonkt.LineString
import geojsonkt.Point

fun LineString.along(distance: Double, units: String): Feature<Point> {
    var travelled = 0.0
    for(i in coordinates.indices) {
        if(distance >= travelled && i == coordinates.lastIndex) break

        if(distance == travelled) return Feature(Point(coordinates[i]))

        if(travelled > distance) {
            val overshot = distance - travelled
            val direction = coordinates[i].bearing(coordinates[i - 1]) - 180
            val interpolated = coordinates[i].destination(overshot, direction, units)
            return Feature(Point(interpolated))
        }

        travelled += coordinates[i].distance(coordinates[i + 1])
    }

    return Feature(Point(coordinates.last()))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Feature<LineString>.along(distance: Double, units: String): Feature<Point> =
        geometry.along(distance, units)