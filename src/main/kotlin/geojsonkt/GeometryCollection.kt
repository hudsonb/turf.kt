package geojsonkt

data class GeometryCollection(val geometries: ArrayList<Geometry>, override val bbox: BBox? = null) :
        MutableList<Geometry> by geometries,
        Geometry {
    companion object;

    constructor(geometries: Collection<Geometry>, bbox: BBox? = null) : this(ArrayList(geometries), bbox)

    override val type = "GeometryCollection"

    override fun equals(other: Any?): Boolean {
        if(this === other) return true
        if(javaClass != other?.javaClass) return false

        other as GeometryCollection

        if(type != other.type) return false
        if(geometries != other.geometries) return false

        return true
    }

    override fun hashCode(): Int {
        var result = geometries.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}

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

    return FeatureCollection(features)
}

/**
 * Returns a geometry collection containing the specified geometries.
 */
fun geometryCollectionOf(vararg geometries: Geometry) = GeometryCollection(geometries.toList())