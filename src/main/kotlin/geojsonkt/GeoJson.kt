package geojsonkt


interface GeoJson {
    val type: String

    val bbox: BBox?
}

interface Geometry : GeoJson

