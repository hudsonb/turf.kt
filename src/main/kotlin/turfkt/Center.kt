package turfkt

import geojsonkt.*

private fun center(geojson: GeoJson): Position {
    val ext = geojson.bbox

    val x = (ext.southWest[0] + ext.northEast[0]) / 2
    val y = (ext.southWest[1] + ext.northEast[1]) / 2

    return Position(x, y)
}

/**
 * Returns the absolute center point of the feature.
 */
fun Feature<*>.center(): Position = center(this)

/**
 * Returns the absolute center point of all the features in the collection.
 */
fun FeatureCollection.center(): Position = center(this)

/**
 * Returns the absolute center point of all the geometries in the collection.
 */
fun GeometryCollection.center(): Position = center(this)

/**
 * Returns the absolute center point of all the geometries in the collection.
 */
fun List<Geometry>.center(): Position = center(GeometryCollection(this))