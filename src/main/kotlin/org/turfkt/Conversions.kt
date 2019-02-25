package org.turfkt

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

/**
 * Earth Radius used with the Harvesine formula and approximates using a spherical (non-ellipsoid) Earth.
 * @type {number}
 */
const val EARTH_RADIUS = 6371008.8

/**
 * Unit of measurement factors using a spherical (non-ellipsoid) earth radius.
 *
 * @memberof helpers
 * @type {Object}
 */
private val factors = mapOf(
    "centimeters" to EARTH_RADIUS * 100,
    "centimetres" to EARTH_RADIUS * 100,
    "degrees" to EARTH_RADIUS / 111325,
    "feet" to EARTH_RADIUS * 3.28084,
    "inches" to EARTH_RADIUS * 39.370,
    "kilometers" to EARTH_RADIUS / 1000,
    "kilometres" to EARTH_RADIUS / 1000,
    "meters" to EARTH_RADIUS,
    "metres" to EARTH_RADIUS,
    "miles" to EARTH_RADIUS / 1609.344,
    "millimeters" to EARTH_RADIUS * 1000,
    "millimetres" to EARTH_RADIUS * 1000,
    "nauticalmiles" to EARTH_RADIUS / 1852,
    "radians" to 1.0,
    "yards" to EARTH_RADIUS / 1.0936
)

/**
 * Convert a distance measurement (assuming a spherical Earth) from radians to a more friendly unit.
 * Valid units: miles, nauticalmiles, inches, yards, meters, metres, kilometers, centimeters, feet
 *
 * @name radiansToLength
 * @param {number} radians in radians across the sphere
 * @param {string} [units="kilometers"] can be degrees, radians, miles, or kilometers inches, yards, metres,
 * meters, kilometres, kilometers.
 * @returns {number} distance
 */
fun radiansToLength(radians: Double, units: String = "kilometers"): Double {
    if(!factors.containsKey(units)) throw IllegalArgumentException("Unrecognized units: $units")
    val factor = factors[units] ?: throw IllegalStateException("Factor for units $units was null")
    return radians * factor
}

/**
 * Convert a distance measurement (assuming a spherical Earth) from a real-world unit into radians
 * Valid units: miles, nauticalmiles, inches, yards, meters, metres, kilometers, centimeters, feet
 *
 * @name lengthToRadians
 * @param {number} distance in real units
 * @param {string} [units="kilometers"] can be degrees, radians, miles, or kilometers inches, yards, metres,
 * meters, kilometres, kilometers.
 * @returns {number} radians
 */
fun lengthToRadians(distance: Double, units: String = "kilometers"): Double {
    if(!factors.containsKey(units)) throw IllegalArgumentException("Unrecognized units: $units")
    val factor = factors[units] ?: throw IllegalStateException("Factor for units $units was null")
    return distance / factor
}