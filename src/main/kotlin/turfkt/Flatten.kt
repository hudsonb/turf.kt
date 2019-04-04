package turfkt

import geojsonkt.*

/**
 * Flattens this [MultiPoint] into a [GeometryCollection] consisting of [Point] geometries.
 *
 * @return A [GeometryCollection] consisting of [Point] geometries.
 */
fun MultiPoint.flatten(): GeometryCollection = GeometryCollection(coordinates.map { Point(it) })

/**
 * Flattens this [MultiLineString] into a [GeometryCollection] consisting of [LineString] geometries.
 *
 * @return A [GeometryCollection] consisting of [LineString] geometries.
 */
fun MultiLineString.flatten(): GeometryCollection = GeometryCollection(coordinates.map { LineString(it) })

/**
 * Flattens the [MultiPolygon] into a [GeometryCollection] consisting of [Polygon] geometries.
 *
 * @return A [GeometryCollection] consisting of [Polygon] geometries.
 */
fun MultiPolygon.flatten(): GeometryCollection = GeometryCollection(coordinates.map { Polygon(it) })

/**
 * Flattens any complex geometry contained in this [GeometryCollection] into a [GeometryCollection] consisting of only
 * non-complex geometries.
 *
 * @return A [GeometryCollection] consisting of non-complex geometries.
 */
fun GeometryCollection.flatten(): GeometryCollection {
    val geos = arrayListOf<Geometry>()
    for(g in geometries) {
        when(g) {
            is MultiPoint -> geos.addAll(g.flatten())
            is MultiLineString -> geos.addAll(g.flatten())
            is MultiPolygon -> geos.addAll(g.flatten())
            is GeometryCollection -> geos.addAll(g.flatten().geometries)

            is Point,
            is LineString,
            is Polygon -> geos += g
        }
    }

    return GeometryCollection(geos)
}

/**
 * Flattens this feature's geometry, returning a feature collection containing only features wrapping non-complex geometries.
 *
 * @return A [FeatureCollection] consisting of features with non-complex geometries.
 */
fun Feature<*>.flatten(): FeatureCollection {
    val features = when(geometry) {
        is MultiPoint -> geometry.flatten().map { Feature(it, properties) }
        is MultiLineString -> geometry.flatten().map { Feature(it, properties) }
        is MultiPolygon -> geometry.flatten().map { Feature(it, properties) }
        is GeometryCollection -> geometry.flatten().map { Feature(it, properties) }

        is Point,
        is LineString,
        is Polygon -> listOf(Feature(geometry, properties))

        else -> error("Unrecognized geometry type: ${geometry.type}")
    }

    return FeatureCollection(features)
}