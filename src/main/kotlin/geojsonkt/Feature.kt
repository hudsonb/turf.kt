package geojsonkt

data class Feature<out G : Geometry>(val geometry: G, val properties: MutableMap<String, Any> = mutableMapOf(),
                                     val id: String? = null, override val bbox: BBox? = null) : GeoJson {
    companion object;

    override val type = "Feature"

}

operator fun Feature<*>.get(key: String): Any? = properties[key]
operator fun Feature<*>.set(key: String, value: Any) {
    properties[key] = value
}