package org.turfkt

import geojsonkt.*

fun getEnvelope(geojson: GeoJson): Polygon = when(geojson) {
    is Polygon, 
    is MultiPolygon,
    is LineString,
    is MultiPoint,
    is MultiLineString -> geojson.bbox().toPolygon()

    is Point -> throw UnsupportedOperationException("Can not BBox on Point Geometry type: ${geojson::class.java.name}")
    else -> throw UnsupportedOperationException("Can not BBox unrecognized Geometry type: ${geojson::class.java.name}")
}

fun Geometry.envelope(): Polygon = getEnvelope(this)

fun FeatureCollection.envelope(): Polygon = getEnvelope(this)

fun GeometryCollection.envelope(): Polygon = getEnvelope(this)
