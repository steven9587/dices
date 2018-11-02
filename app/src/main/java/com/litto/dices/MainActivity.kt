package com.litto.dices

import android.app.Activity
import android.content.Context
import android.graphics.Matrix
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var shake : MediaPlayer
    lateinit var dices : Array<MediaPlayer>
    lateinit var matrix : Matrix
    var degree = 15f
    private val sensorListener: SensorEventListener? = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event != null) {
                var x = Math.abs(event.values[0])
                var y = Math.abs(event.values[1])
                var z = Math.abs(event.values[2])
                logd("$x / $y / $z")
                if (x > 3) {
                    shakeIt()
                }
            }
        }
    }

    private fun shakeIt() {
//        shake?.start()
        dices.get(Random().nextInt(5)).start()
//        cup.scaleType = ImageView.ScaleType.MATRIX
//        matrix.postRotate(degree)
////        degree = if (degree == 15f) 0f else 15f
////        cup.imageMatrix = matrix
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        matrix = Matrix()
        shake = MediaPlayer.create(this, R.raw.shake_dice)
        dices = arrayOf(MediaPlayer.create(this, R.raw.dice1),
            MediaPlayer.create(this, R.raw.dice2),
            MediaPlayer.create(this, R.raw.dice3),
            MediaPlayer.create(this, R.raw.dice4),
            MediaPlayer.create(this, R.raw.dice5))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        var sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

fun Activity.logd(str : String) {
    Log.d(this::class.java.simpleName, str)
}