package geojsonkt


interface GeoJson {
    companion object

    val type: String
}

interface Geometry : GeoJson {
    companion object
}