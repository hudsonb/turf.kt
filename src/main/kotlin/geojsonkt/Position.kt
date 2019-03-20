package geojsonkt

fun Position(longitude: Double, latitude: Double, elevation: Double = Double.NaN) =
        Position(doubleArrayOf(longitude, latitude, elevation))

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Position(private val values: DoubleArray) {
    operator fun get(i: Int) = values[i]

    val longitude
        get() = values[0]

    val latitude
        get() = values[1]

    val elevation
        get() = when {
            values.size >= 3 -> values[2]
            else -> Double.NaN
        }
}