package org.turfkt

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class Position(private val values: DoubleArray) {
    operator fun get(i: Int) = values[0]

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

fun Position(longitude: Double, latitude: Double, elevation: Double = Double.NaN) =
        Position(doubleArrayOf(longitude, latitude, elevation))

interface GeoJson {
    val type: String
}

interface Geometry<out T> : GeoJson {
    val coordinates: T
}

data class Point(override val coordinates: Position) : Geometry<Position> {
    constructor(longitude: Double, latitude: Double, elevation: Double = 0.0) : this(Position(longitude, latitude, elevation))

    override val type = "Point"
}

data class LineString(override val coordinates: Array<Position>) : Geometry<Array<Position>> {
    override val type = "LineString"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineString

        if (!coordinates.contentEquals(other.coordinates)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

data class Polygon(override val coordinates: Array<Array<Position>>) : Geometry<Array<Array<Position>>> {
    override val type = "Polygon"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polygon

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

data class MultiPoint(override val coordinates: Array<Position>) : Geometry<Array<Position>> {
    override val type = "MultiPoint"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiPoint

        if (!coordinates.contentEquals(other.coordinates)) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = coordinates.contentHashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

data class MultiLineString(override val coordinates: Array<Array<Position>>) : Geometry<Array<Array<Position>>> {
    override val type = "MultiLineString"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiLineString

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

data class MultiPolygon(override val coordinates: Array<Array<Array<Position>>>) : Geometry<Array<Array<Array<Position>>>> {
    override val type = "MultiPolygon"
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

data class GeometryCollection(val geometries: Array<Geometry<*>>) : GeoJson {
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

data class Feature(val geometry: Geometry<*>, val properties: Map<String, *> = emptyMap<String, Any>()) : GeoJson {
    override val type = "Feature"

    var id: String? = null
}

data class FeatureCollection(val features: Array<Feature>) : GeoJson {
    override val type = "FeatureCollection"
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