//IT19011844 Hemachandra MGSP

package com.example.it19011844_lab5

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(),SensorEventListener {
    //Views
    private lateinit var tvGravity: ArrayList<TextView>
    private lateinit var tvAcceleration: ArrayList<TextView>

    //Buttons
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    //View Ids
    private var idGravity:ArrayList<Int> = arrayListOf(R.id.tv_g_x,R.id.tv_g_y, R.id.tv_g_x)
    private var idAcceleration:ArrayList<Int> = arrayListOf(R.id.tv_a_x,R.id.tv_a_y, R.id.tv_a_x)

    //Sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorGravity: Sensor
    private lateinit var sensorAcceleration: Sensor

    //Sensor Data
    private var gravityData: SensorData? = null
    private var accelerationData: SensorData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initSensor()
    }

    private fun initViews()
    {
        //Init Tv Accelerations
        for(i in idAcceleration)
        {
            tvAcceleration.add(findViewById(i))
        }

        //Init Tv Gravity
        for(i in idGravity)
        {
            tvGravity.add(findViewById(i))
        }

        //Init Buttons
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)

        //Click Listener
        btnStart.setOnClickListener {
            //StartSensorListener
            registerListener()
            btnStart.isEnabled = false
            btnStop.isEnabled = true
        }
        btnStop.setOnClickListener {
            //StopSensorListener
            unregisterListener()
            btnStart.isEnabled = true
            btnStop.isEnabled = false
        }
    }

    private fun initSensor()
    {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
        {
            sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
        {
            sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        }
    }

    private fun registerListener()
    {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null)
        {
            sensorManager.registerListener(this, sensorGravity, SensorManager.SENSOR_DELAY_NORMAL)
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null)
        {
            sensorManager.registerListener(this, sensorAcceleration, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun unregisterListener()
    {
        sensorManager.unregisterListener(this, sensorGravity)
        sensorManager.unregisterListener(this, sensorAcceleration)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_GRAVITY)
        {
            getGravityData(event)
        }
        else if(event!!.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION)
        {
            getAccelerationData(event)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    private fun getGravityData(e:SensorEvent?)
    {
        if(gravityData == null)
        {
            gravityData = SensorData(e!!.values[0],e!!.values[1],e!!.values[2],e!!.timestamp)
        }
        else
        {
            gravityData!!.x1 = e!!.values[0]
            gravityData!!.x2 = e!!.values[1]
            gravityData!!.x3 = e!!.values[2]
        }

        tvGravity[0].text = "x1: ${"%.2f".format(gravityData!!.x1)}"
        tvGravity[1].text = "x2: ${"%.2f".format(gravityData!!.x2)}"
        tvGravity[2].text = "x3: ${"%.2f".format(gravityData!!.x3)}"
    }

    private fun getAccelerationData(e:SensorEvent?)
    {
        if(accelerationData == null)
        {
            accelerationData = SensorData(e!!.values[0],e!!.values[1],e!!.values[2],e!!.timestamp)
        }
        else
        {
            accelerationData!!.x1 = e!!.values[0]
            accelerationData!!.x2 = e!!.values[1]
            accelerationData!!.x3 = e!!.values[2]
        }

        tvAcceleration[0].text = "x1: ${"%.2f".format(accelerationData!!.x1)}"
        tvAcceleration[1].text = "x2: ${"%.2f".format(accelerationData!!.x2)}"
        tvAcceleration[2].text = "x3: ${"%.2f".format(accelerationData!!.x3)}"
    }
}