package turfkt

import geojsonkt.BBox
import geojsonkt.Position
import geojsonkt.latitude
import geojsonkt.longitude

/**
 * Returns the minimum square bounding box that would contain this bounding box.
 */
fun BBox.square(): BBox {
    val west = southWest.longitude
    val south = southWest.latitude
    val east = northEast.longitude
    val north = northEast.latitude

    val southEast = Position(east, south)
    val northWest = Position(west, north)

    val horizontalDistance = southWest.distance(southEast)
    val verticalDistance = southWest.distance(northWest)

    return when {
        horizontalDistance >= verticalDistance -> {
            val verticalMidpoint = (south + north) / 2
            BBox(west,
                    verticalMidpoint - ((east - west) / 2),
                    east,
                    verticalMidpoint + ((east - west) / 2))
        }
        else -> {
            val horizontalMidpoint = (west + east) / 2
            BBox(horizontalMidpoint - ((north - south) / 2),
                    south,
                    horizontalMidpoint + ((north - south) / 2),
                    north)
        }
    }
}