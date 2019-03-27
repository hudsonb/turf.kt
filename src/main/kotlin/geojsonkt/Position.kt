package geojsonkt

fun Position(longitude: Double, latitude: Double, elevation: Double = Double.NaN) =
        Position(doubleArrayOf(longitude, latitude, elevation))

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Position(private val values: DoubleArray) {
    operator fun get(i: Int) = values[i]

    val x
        get() = values[0]

    val y
        get() = values[1]

    val z
        get() = when {
            values.size >= 3 -> values[2]
            else -> Double.NaN
        }
}

inline val Position.longitude get() = x
inline val Position.latitude get() = y
inline val Position.elevation get() = z