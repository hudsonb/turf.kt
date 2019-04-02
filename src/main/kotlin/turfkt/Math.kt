package turfkt

import kotlin.math.PI

const val RADIANS_TO_DEGREES_FACTOR = 180 / PI
const val DEGREES_TO_RADIANS_FACTOR = PI / 180

@Suppress("NOTHING_TO_INLINE")
inline fun Double.toDegrees() = this * RADIANS_TO_DEGREES_FACTOR


@Suppress("NOTHING_TO_INLINE")
inline fun Double .toRadians() = this * DEGREES_TO_RADIANS_FACTOR

