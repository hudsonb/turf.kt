package geojsonkt

data class FeatureCollection(val features: Array<Feature<*>>, override val bbox: BBox? = null) : GeoJson {
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

val FeatureCollection.size: Int get() = features.size

val FeatureCollection.indices: IntRange get() = features.indices

operator fun FeatureCollection.get(i: Int) = features[i]

operator fun FeatureCollection.iterator(): Iterator<Feature<*>> = features.iterator()