package edu.uark.fayettefinds

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.TypedValue
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("app_settings", Context.MODE_PRIVATE)

        val fontSizePreviewTextView = findViewById<TextView>(R.id.fontSizePreviewTextView)
        val fontSizeSeekBar = findViewById<SeekBar>(R.id.fontSizeSeekBar)
        fontSizeSeekBar.progress = sharedPreferences.getInt("font_size", 14) // Default font size

        fontSizeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Update font size preview (optional)
                fontSizePreviewTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, progress.toFloat())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // No action needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                val fontSize = seekBar?.progress ?: 14
                sharedPreferences.edit().putInt("font_size", fontSize).apply()
            }
        })
    }
}