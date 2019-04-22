package turfkt

/**
 * Earth Radius used with the Harvesine formula and approximates using a spherical (non-ellipsoid) Earth.
 */
const val EARTH_RADIUS = 6371008.8

/**
 * Unit of measurement factors using a spherical (non-ellipsoid) earth radius.
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
 * @param radians Radians across the sphere
 * @param units The length units to convert to. Can be degrees, radians, miles, or kilometers inches, yards, metres,
 * meters, kilometres, kilometers. Default to kilometers.
 * @returns The length in the given units.
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
 * @param distance The distance in real units
 * @param units The units of the given distance. Can be degrees, radians, miles, or kilometers inches, yards, metres,
 * meters, kilometres, kilometers. Defaults to kilometers.
 * @returns The length in radians.
 */
fun lengthToRadians(distance: Double, units: String = "kilometers"): Double {
    if(!factors.containsKey(units)) throw IllegalArgumentException("Unrecognized units: $units")
    val factor = factors[units] ?: throw IllegalStateException("Factor for units $units was null")
    return distance / factor
}

/**
 * Converts a length to the requested unit.
 * Valid units: miles, nauticalmiles, inches, yards, meters, metres, kilometers, centimeters, feet
 *
 * @param length length to be converted
 * @param originalUnit [originalUnit="kilometers"] of the length
 * @param finalUnit [finalUnit="kilometers"] returned unit
 * @return the converted length
 */
fun convertLength(length: Double, originalUnit: String = "kilometers", finalUnit: String = "kilometers"): Double {
    require(length >= 0) { "length must be a positive number" }
    return radiansToLength(lengthToRadians(length, originalUnit), finalUnit)
}