package com.example.medicaldevice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

class MainActivity : AppCompatActivity() {
    private lateinit var mqttClient: MqttAndroidClient
    private lateinit var textView: TextView
    private lateinit var textViewBPM: TextView
    private lateinit var textViewSPO2: TextView
    private lateinit var textViewRoomTemp: TextView
    private lateinit var textViewLight: TextView
    private lateinit var tempValueList: ArrayList<String>
    private lateinit var series: LineGraphSeries<DataPoint>
    private lateinit var seriesBPM: LineGraphSeries<DataPoint>
    private lateinit var seriesSPO2: LineGraphSeries<DataPoint>
    private lateinit var seriesRoomTemp: LineGraphSeries<DataPoint>
    private lateinit var seriesLight: LineGraphSeries<DataPoint>
    private var count: Int = 1
    private var countBPM: Int = 1
    private var countSPO2: Int = 1
    private var countRoomTemp: Int = 1
    private var countLight: Int = 1

    companion object {
        const val TAG = "AndroidMqttClient"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var extras: Bundle = intent.extras!!
        if(extras.get("id")==0){
            setTitle("Live Data Feed - Chamal")
        }
        else{
            setTitle("Live Data Feed - Ayesha")
        }

        this.connect(this)
        // Body Temperature
        textView = findViewById(R.id.textViewBodyTemp)
        val graph = findViewById<View>(R.id.graph) as GraphView
        series = LineGraphSeries(arrayOf(DataPoint(0.0,30.0)))
        graph.addSeries(series)
        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMinX(0.0)
        graph.viewport.setMaxX(40.0)
        graph.viewport.setScalable(true)
        graph.viewport.setScrollable(true)
        graph.viewport.setScalableY(true)
        graph.viewport.setScrollableY(true)

        // BPM
        textViewBPM = findViewById(R.id.textViewBPM)
        val graphBPM = findViewById<View>(R.id.graphBPM) as GraphView
        seriesBPM = LineGraphSeries(arrayOf(DataPoint(0.0,60.0)))
        graphBPM.addSeries(seriesBPM)
        graphBPM.viewport.isXAxisBoundsManual = true
        graphBPM.viewport.setMinX(0.0)
        graphBPM.viewport.setMaxX(40.0)
        graphBPM.viewport.setScalable(true)
        graphBPM.viewport.setScrollable(true)
        graphBPM.viewport.setScalableY(true)
        graphBPM.viewport.setScrollableY(true)

        // SPO2
        textViewSPO2 = findViewById(R.id.textViewSPO2)
        val graphSPO2 = findViewById<View>(R.id.graphSPO2) as GraphView
        seriesSPO2 = LineGraphSeries(arrayOf(DataPoint(0.0,100.0)))
        graphSPO2.addSeries(seriesSPO2)
        graphSPO2.viewport.isXAxisBoundsManual = true
        graphSPO2.viewport.setMinX(0.0)
        graphSPO2.viewport.setMaxX(40.0)
        graphSPO2.viewport.setScalable(true)
        graphSPO2.viewport.setScrollable(true)
        graphSPO2.viewport.setScalableY(true)
        graphSPO2.viewport.setScrollableY(true)

        // Room temp
        textViewRoomTemp = findViewById(R.id.textViewRoomTemp)
        val graphRoomTemp = findViewById<View>(R.id.graphRoomTemp) as GraphView
        seriesRoomTemp = LineGraphSeries(arrayOf(DataPoint(0.0,100.0)))
        graphRoomTemp.addSeries(seriesRoomTemp)
        graphRoomTemp.viewport.isXAxisBoundsManual = true
        graphRoomTemp.viewport.setMinX(0.0)
        graphRoomTemp.viewport.setMaxX(40.0)
        graphRoomTemp.viewport.setScalable(true)
        graphRoomTemp.viewport.setScrollable(true)
        graphRoomTemp.viewport.setScalableY(true)
        graphRoomTemp.viewport.setScrollableY(true)

        // Light
        textViewLight = findViewById(R.id.textViewLight)
        val graphLight = findViewById<View>(R.id.graphLight) as GraphView
        seriesLight = LineGraphSeries(arrayOf(DataPoint(0.0,100.0)))
        graphLight.addSeries(seriesLight)
        graphLight.viewport.isXAxisBoundsManual = true
        graphLight.viewport.setMinX(0.0)
        graphLight.viewport.setMaxX(40.0)
        graphLight.viewport.setScalable(true)
        graphLight.viewport.setScrollable(true)
        graphLight.viewport.setScalableY(true)
        graphLight.viewport.setScrollableY(true)
    }

    fun connect(context: Context) {
        val serverURI = "tcp://YOUR-MQTT-IP:1883"
        mqttClient = MqttAndroidClient(context, serverURI, "MedicalDeviceAndroid")
        mqttClient.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                Log.d(TAG, "Receive message: ${message.toString()} from topic: $topic")
                when(topic){
                    "temp" -> {
                        textView.setText("Current Value: " + message.toString() + "°C")
                        count++;
                        var value: Double = message.toString().toDouble()
                        Log.d(TAG, "Receive message: ${value.toString()} from topic: $topic")
                        series.appendData(DataPoint(count.toDouble(),value),true,40)
                    }
                    "bpm" -> {
                        textViewBPM.setText("Current Value: " + message.toString() + "bpm")
                        countBPM++;
                        var value: Double = message.toString().toDouble()
                        Log.d(TAG, "Receive message: ${value.toString()} from topic: $topic")
                        seriesBPM.appendData(DataPoint(countBPM.toDouble(),value),true,40)
                    }
                    "spo2" -> {
                        textViewSPO2.setText("Current Value: " + message.toString() + "%")
                        countSPO2++;
                        var value: Double = message.toString().toDouble()
                        Log.d(TAG, "Receive message: ${value.toString()} from topic: $topic")
                        seriesSPO2.appendData(DataPoint(countSPO2.toDouble(),value),true,40)
                    }
                    "ontemp" -> {
                        textViewRoomTemp.setText("Current Value: " + message.toString() + "°C")
                        countRoomTemp++;
                        var value: Double = message.toString().toDouble()
                        Log.d(TAG, "Receive message: ${value.toString()} from topic: $topic")
                        seriesRoomTemp.appendData(DataPoint(countRoomTemp.toDouble(),value),true,40)
                    }
                    "light" -> {
                        textViewLight.setText("Current Value: " + message.toString() + "%")
                        countLight++;
                        var value: Double = message.toString().toDouble()
                        Log.d(TAG, "Receive message: ${value.toString()} from topic: $topic")
                        seriesLight.appendData(DataPoint(countLight.toDouble(),value),true,40)
                    }
                }

            }

            override fun connectionLost(cause: Throwable?) {
                Log.d(TAG, "Connection lost ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {

            }
        })
        val options = MqttConnectOptions()
        try {
            mqttClient.connect(options, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Connection success")
                    subscribe("temp")
                    subscribe("bpm")
                    subscribe("spo2")
                    subscribe("ontemp")
                    subscribe("light")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Connection failure")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun subscribe(topic: String, qos: Int = 1) {
        try {
            mqttClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Subscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to subscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unsubscribe(topic: String) {
        try {
            mqttClient.unsubscribe(topic, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Unsubscribed to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to unsubscribe $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 1, retained: Boolean = false) {
        try {
            val message = MqttMessage()
            message.payload = msg.toByteArray()
            message.qos = qos
            message.isRetained = retained
            mqttClient.publish(topic, message, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "$msg published to $topic")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to publish $msg to $topic")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun disconnect() {
        try {
            mqttClient.disconnect(null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(TAG, "Disconnected")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(TAG, "Failed to disconnect")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}