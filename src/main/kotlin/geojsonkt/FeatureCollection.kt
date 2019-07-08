package geojsonkt

/**
 * Returns a feature collection containing the specified features.
 */
fun featureCollectionOf(vararg features: Feature<*>) = FeatureCollection(features.toList())

data class FeatureCollection(val features: ArrayList<Feature<*>>) :
        MutableList<Feature<*>> by features,
        GeoJson {
    companion object;

    constructor(features: Collection<Feature<*>>, bbox: BBox? = null) : this(ArrayList(features))

    override val type = "FeatureCollection"


    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as FeatureCollection

        if(type != other.type) return false
        if(features != other.features) return false

        return true
    }

    override fun hashCode(): Int {
        var result = features.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

/**
 * Returns a GeometryCollection consisting of the geometry of each feature in this collection.
 */
fun FeatureCollection.toGeometryCollection() =
    GeometryCollection(features.map { it.geometry })