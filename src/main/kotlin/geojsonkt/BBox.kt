package geojsonkt

import java.lang.IllegalStateException


@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class BBox(private val values: DoubleArray) {
    val southWest: Position
        get() = when(values.size) {
            4 -> Position(values[0], values[1])
            6 -> Position(values[0], values[1], values[2])
            else -> throw IllegalStateException("Unexpected number of values, 4 or 6 expected but ${values.size} were found.")
        }

    val northWest: Position
        get()  = when(values.size) {
            4 -> Position(values[2], values[3])
            6 -> Position(values[3], values[4], values[5])
            else -> throw IllegalStateException("Unexpected number of values, 4 or 6 expected but ${values.size} were found.")
        }
}

fun BBox(swlon: Double, swlat: Double, nelon: Double, nelat: Double) =
        BBox(doubleArrayOf(swlon, swlat, nelon, nelat))

fun BBox(swlon: Double, swlat: Double, swelevation: Double,
         nelon: Double, nelat: Double, neelevation: Double) =
        BBox(doubleArrayOf(swlon, swlat, swelevation, nelon, nelat, neelevation))

fun BBox(sw: Position, ne: Position): BBox {
    if(sw.elevation.isNaN() || ne.elevation.isNaN())
        return BBox(doubleArrayOf(sw.longitude, sw.latitude, ne.longitude, ne.latitude))

    return BBox(doubleArrayOf(sw.longitude, sw.latitude, sw.elevation, ne.longitude, ne.latitude, ne.elevation))
}