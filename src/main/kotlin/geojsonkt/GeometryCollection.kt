package geojsonkt


data class GeometryCollection(val geometries: Array<Geometry>, override val bbox: BBox? = null) : GeoJson {
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