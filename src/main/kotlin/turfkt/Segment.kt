package turfkt

import geojsonkt.*

/**
 * Returns a [GeometryCollection] consisting of 2-vertex [LineString] segments derived from this [LineString].
 */
fun LineString.segment(): GeometryCollection {
    val geometries = ArrayList<Geometry>()
    for(coords in coordinates.segment()) {
        geometries += LineString(coords)
    }

    return GeometryCollection(geometries)
}

/**
 * Returns a [GeometryCollection] consisting of 2-vertex [LineString] segments derived from this [MultiLineString].
 */
fun MultiLineString.segment(): GeometryCollection {
    val geometries = ArrayList<Geometry>()

    for(ls in coordinates) {
        for(coords in ls.segment()) {
            geometries += LineString(coords)
        }
    }

    return GeometryCollection(geometries)
}

/**
 * Returns a [GeometryCollection] consisting of 2-vertex [LineString] segments derived from this [Polygon].
 */
fun Polygon.segment(): GeometryCollection {
    val geometries = ArrayList<Geometry>()
    for(ring in coordinates) {
        for(coords in ring.segment()) {
            geometries += LineString(coords)
        }
    }

    return GeometryCollection(geometries)
}

/**
 * Returns a [GeometryCollection] consisting of 2-vertex [LineString] segments derived from this [MultiPolygon].
 */
fun MultiPolygon.segment(): GeometryCollection {
    val geometries = ArrayList<Geometry>()
    for(polygon in coordinates) {
        for(ring in polygon) {
            for(coords in ring.segment()) {
                geometries += LineString(coords)
            }
        }
    }

    return GeometryCollection(geometries)
}

/**
 * Returns an array of 2-vertex segments derived from this array.
 */
fun Array<Position>.segment(): Array<Array<Position>> {
    val segments = ArrayList<Array<Position>>()

    var previous = first()
    var current: Position
    for(i in 1 until size) {
        current = get(i)
        segments += arrayOf(previous, current)
        previous = current
    }

    return segments.toTypedArray()
}