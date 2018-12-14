package com.butajlo.lightsensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry.Registrar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class LightSensorPlugin(context: Context) : EventChannel.StreamHandler {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    private var listener: SensorEventListener? = null

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            EventChannel(registrar.messenger(), "com.butajlo.lightsensor/stream")
                    .setStreamHandler(LightSensorPlugin(registrar.context()))
        }

        @JvmStatic
        fun createSensorListener(events: EventChannel.EventSink): SensorEventListener {
            return object : SensorEventListener {
                override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) = Unit

                override fun onSensorChanged(event: SensorEvent) {
                    events.success(event.values[0])
                }
            }
        }
    }

    override fun onListen(args: Any?, events: EventChannel.EventSink) {
        listener = createSensorListener(events)
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onCancel(args: Any?) {
        listener?.apply {
            sensorManager.unregisterListener(this)
        }
    }
}
