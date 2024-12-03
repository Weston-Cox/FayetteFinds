package edu.uark.fayettefinds.Util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Typeface
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.util.GeoPoint

class AddressOverlay(
    private val context: Context,
    private val geoPoint: GeoPoint,
    private val address: String
) : Overlay() {

    private val paint = Paint().apply {
        color = context.resources.getColor(android.R.color.black, null)
        textSize = 40f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    override fun draw(canvas: Canvas, mapView: MapView, shadow: Boolean) {
        if (shadow) return

        val screenPoint = Point()
        mapView.projection.toPixels(geoPoint, screenPoint)

        canvas.drawText(address, 55.0F, 40.0F, paint)
    }
}