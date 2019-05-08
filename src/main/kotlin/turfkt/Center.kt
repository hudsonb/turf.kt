package turfkt

import geojsonkt.*

private fun center(geojson: GeoJson): Position {
    val ext = geojson.bbox!!

    val x = (ext.southWest[0] + ext.northEast[0]) / 2
    val y = (ext.southWest[1] + ext.northEast[1]) / 2

    return Position(x, y)
}

fun Feature<*>.center(): Position = center(this)

fun FeatureCollection.center(): Position = center(this)