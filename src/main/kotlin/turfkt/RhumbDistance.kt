package turfkt

import geojsonkt.Feature
import geojsonkt.Point
import geojsonkt.Position
import geojsonkt.coordinate
import kotlin.math.*

private const val MEAN_EARTH_RADIUS = 6371e3

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Position.rhumbDistance(to: Position, units: String = "kilometers"): Double {
    // Compensate the crossing of the 180th meridian (https://macwright.org/2016/09/26/the-180th-meridian.html)
    // Solution from https://github.com/mapbox/mapbox-gl-js/issues/3250#issuecomment-294887678
    val dx = to.x + if(to.x - x > 180) -360 else if(x - to.x > 180) 360 else 0
    val distanceInMeters = calculateRhumbDistance(Position(dx, to.y), to)
    return convertLength(distanceInMeters, "meters", units)
}

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Position.rhumbDistance(to: Point, units: String = "kilometers"): Double =
        rhumbDistance(to.coordinate, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Position.rhumbDistance(to: Feature<Point>, units: String = "kilometers"): Double =
    rhumbDistance(to.geometry.coordinate, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Point.rhumbDistance(to: Position, units: String = "kilometers"): Double =
        coordinate.rhumbDistance(to, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Point.rhumbDistance(to: Point, units: String = "kilometers"): Double =
        rhumbDistance(to.coordinate, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Point.rhumbDistance(to: Feature<Point>, units: String = "kilometers"): Double =
        rhumbDistance(to.geometry.coordinate, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Feature<Point>.rhumbDistance(to: Position, units: String = "kilometers"): Double =
        geometry.rhumbDistance(to, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Feature<Point>.rhumbDistance(to: Point, units: String = "kilometers"): Double =
        rhumbDistance(to.coordinate, units)

/**
 * Calculates the distance along a rhumb line between two {@link Point|points} in degrees, radians,
 * miles, or kilometers.
 *
 * @name rhumbDistance
 * @param to destination point
 * @param units The desired units. Can be degrees, radians, miles, or kilometers
 * @return The distance between the two points in the givne units
 */
fun Feature<Point>.rhumbDistance(to: Feature<Point>, units: String = "kilometers"): Double =
        rhumbDistance(to.geometry.coordinate, units)

/**
 * Returns the distance travelling from the origin point to the destination point along a rhumb line.
 *
 * @param origin origin point.
 * @param destination destination point.
 *
 * @return The distance in km between this point and destination point.
 */
private fun calculateRhumbDistance(origin: Position, destination: Position, radius: Double = MEAN_EARTH_RADIUS): Double {
    // φ => phi
    // λ => lambda
    // ψ => psi
    // Δ => Delta
    // δ => delta
    // θ => theta

    val phi1 = origin.y.toRadians()
    val phi2 = destination.y.toRadians()
    val DeltaPhi = phi2 - phi1
    var DeltaLambda = abs(destination.x - origin.x).toRadians()
    // If dLon over 180° take shorter rhumb line across the anti-meridian:
    if (DeltaLambda > PI) DeltaLambda -= 2 * PI

    // In Mercator projection, longitude distances shrink by latitude; q is the 'stretch factor'
    // q becomes ill-conditioned along E-W line (0/0); use empirical tolerance to avoid it
    val DeltaPsi = ln(tan(phi2 / 2 + PI / 4) / tan(phi1 / 2 + PI / 4))
    val q = if(abs(DeltaPsi) > 10e-12) DeltaPhi / DeltaPsi else cos(phi1)

    // distance is pythagoras on 'stretched' Mercator projection
    val delta = sqrt(DeltaPhi * DeltaPhi + q * q * DeltaLambda * DeltaLambda) // angular distance in radians
    return delta * radius
}