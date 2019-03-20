package geojsonkt

import java.lang.IllegalStateException




interface GeoJson {
    val type: String
}

interface Geometry : GeoJson



data class MultiPolygon(val coordinates: Array<Array<Array<Position>>>) : Geometry {
    override val type = "MultiPolygon"

    operator fun get(i: Int) = coordinates[i]
    operator fun contains(pos: Position) = coordinates.any { p -> p.any { ls -> pos in ls } }
    operator fun contains(p: Polygon) = p.coordinates in coordinates

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiPolygon

        if (!coordinates.contentDeepEquals(other.coordinates)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentDeepHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

data class GeometryCollection(val geometries: Array<Geometry>) : GeoJson {
    override val type = "GeometryCollection"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GeometryCollection

        if (!geometries.contentEquals(other.geometries)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = geometries.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

data class Feature<G : Geometry>(val geometry: G, val properties: MutableMap<String, Any> = mutableMapOf()) : GeoJson {
    override val type = "Feature"
    var id: String? = null

    operator fun get(key: String) = properties[key]
    operator fun set(key: String, value: Any) {
        properties[key] = value
    }
}

data class FeatureCollection(val features: Array<Feature<*>>) : GeoJson {
    override val type = "FeatureCollection"

    operator fun get(i: Int) = features[i]
    operator fun contains(f: Feature<*>) = f in features
    operator fun contains(g: Geometry) = g in features.map { it.geometry }

    operator fun iterator() = features.iterator()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FeatureCollection

        if (!features.contentEquals(other.features)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = features.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

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