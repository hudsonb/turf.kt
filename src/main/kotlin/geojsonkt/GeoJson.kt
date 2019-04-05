package geojsonkt


interface GeoJson {
    companion object

    val type: String

    val bbox: BBox?
}

interface Geometry : GeoJson {
    companion object
}