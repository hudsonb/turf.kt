import geojsonkt.*
import turfkt.centroid

fun Feature<*>.centerOfMass(): Position = this.centerOfMass()

fun FeatureCollection.centerOfMass(): Position = this.centerOfMass()

fun GeoJson.centerOfMass(): Position = when (this) {
    is Point -> coordinates
    is Polygon -> {
        val coordsInPoly = this.coordinates
        val centre = coordinates.first().centroid()
        var sx = 0.0
        var sy = 0.0
        var sArea = 0.0
        var pi: Position
        var pj: Position
        var xi: Double
        var xj: Double
        var yi: Double
        var yj: Double
        var a: Double
        var translation = centre
        var neutralizedPoints = coords(this).map {  point -> Position(point[0] - translation.x, point[1] - translation.y) }

        for (i in 0 until coordsInPoly.size) {
            // pi is the current point
            pi = neutralizedPoints.toList()[i]
            xi = pi[0]
            yi = pi[1]

            // pj is the next point (pi+1)
            pj = neutralizedPoints.toList()[i + 1]
            xj = pj[0]
            yj = pj[1]

            // a is the common factor to compute the signed area and the final coordinates
            a = xi * yj - xj * yi

            // sArea is the sum used to compute the signed area
            sArea += a

            // sx and sy are the sums used to compute the final coordinates
            sx += (xi + xj) * a
            sy += (yi + yj) * a
        }

        // Shape has no area: fallback on turf.centroid
        when (sArea) {
            0.0 -> centre
            else -> {
                // Compute the signed area, and factorize 1/6A
                var area = sArea * 0.5
                var areaFactor = 1 / (6 * area)

                // Compute the final coordinates, adding back the values that have been neutralized
                Position(translation[0] + areaFactor * sx, translation[1] + areaFactor * sy )
            }
        }
    }
    // Yet to implement the convex class to appropriately deal with the else it needs convexHull
    else -> throw UnsupportedOperationException("centerOfMass is not supported on this GeoJson type: ${this.type}")
}

private fun coords(g: Geometry): Sequence<Position> = when (g) {
    is Point -> arrayOf(g.coordinate).asSequence()
    is LineString -> g.coordinates.asSequence()
    is Polygon -> g.coordinates.first().asSequence()
    is MultiPoint -> g.coordinates.asSequence()
    is MultiLineString -> g.coordinates.flatten().asSequence()
    is MultiPolygon -> g.coordinates.flatMap { it.first().toList() }.asSequence()
    is GeometryCollection -> g.geometries.asSequence().map { coords(it) }.flatten()
    else -> emptySequence()
}

