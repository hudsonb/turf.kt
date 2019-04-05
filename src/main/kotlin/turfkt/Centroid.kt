package turfkt

import geojsonkt.*

/**
 * Returns the centroid using the mean of all vertices.
 *
 * @return The centroid of all vertices.
 */
fun GeoJson.centroid() {
    when(this) {
        is Geometry -> centroid(this)
        is Feature<*> -> centroid(geometry)
    }
}

/**
 * Returns the centroid using the mean of all vertices.
 *
 * @return The centroid of all vertices.
 */
fun Array<Position>.centroid(): Position {
    var x = 0.0
    var y = 0.0
    for(p in this) {
        x += p.x
        y += p.y
    }

    return Position(x / size, y / size)
}

private fun centroid(geometry: Geometry): Position = when(geometry) {
    is Point -> geometry.coordinate
    is LineString -> geometry.coordinates.centroid()
    is Polygon -> geometry.coordinates.first().centroid()
    is MultiPoint -> geometry.coordinates.centroid()
    is MultiLineString -> geometry.coordinates.flatten().toTypedArray().centroid()
    is MultiPolygon -> geometry.coordinates.flatMap { it.first().toList() }.toTypedArray().centroid()
    is GeometryCollection -> centroid(geometry)

    else -> throw UnsupportedOperationException("centroid is not supported on this geometry type: ${geometry.type}")
}

private fun centroid(gc: GeometryCollection): Position {
    var n = 0
    var x = 0.0
    var y = 0.0
    val coords = coords(gc)
    for(g in gc.geometries) {
        for(p in coords) {
            x += p.x
            y += p.y
            ++n
        }
    }

    return Position(x / n, y / n)
}

private fun coords(g: Geometry): Sequence<Position> = when(g) {
    is Point -> arrayOf(g.coordinate).asSequence()
    is LineString -> g.coordinates.asSequence()
    is Polygon -> g.coordinates.first().asSequence()
    is MultiPoint -> g.coordinates.asSequence()
    is MultiLineString -> g.coordinates.flatten().asSequence()
    is MultiPolygon -> g.coordinates.flatMap { it.first().toList() }.asSequence()
    is GeometryCollection -> g.geometries.asSequence().map { coords(it) }.flatten()
    else -> emptySequence()
}
