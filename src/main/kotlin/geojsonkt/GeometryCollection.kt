package geojsonkt

import sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte1.other


data class GeometryCollection(val geometries: Array<Geometry>, override val bbox: BBox? = null) : Geometry {
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

val GeometryCollection.size: Int get() = geometries.size

val GeometryCollection.indices: IntRange get() = geometries.indices

operator fun GeometryCollection.get(i: Int) = geometries[i]

operator fun GeometryCollection.iterator(): Iterator<Geometry> = geometries.iterator()

operator fun GeometryCollection.plus(other: GeometryCollection): GeometryCollection =
        GeometryCollection(geometries + other.geometries)

fun GeometryCollection.toFeature(properties: MutableMap<String, Any> = mutableMapOf()): Feature<GeometryCollection> =
        Feature(this, properties)

fun GeometryCollection.toFeatureCollection(properties: MutableMap<String, Any> = mutableMapOf()): FeatureCollection {
    val features = ArrayList<Feature<*>>(size)
    for(g in geometries) {
        features += when(g) {
            is Point -> g.toFeature(properties)
            is LineString -> g.toFeature(properties)
            is Polygon -> g.toFeature(properties)
            is MultiPoint -> g.toFeature(properties)
            is MultiLineString -> g.toFeature(properties)
            is MultiPolygon -> g.toFeature(properties)
            is GeometryCollection -> g.toFeature(properties)
            else -> error("Unrecognized geometry type: ${g.type}")
        }
    }

    return FeatureCollection(features.toTypedArray())
}