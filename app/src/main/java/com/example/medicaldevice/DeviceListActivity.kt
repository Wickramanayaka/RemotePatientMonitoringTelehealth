package com.example.medicaldevice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DeviceListActivity : AppCompatActivity() {
    private val deviceList = ArrayList<DeviceModel>()
    private lateinit var deviceAdapter: DeviceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        var device = DeviceModel("Chamal", "+94770219031", "chamal.aw@live.com")
        deviceList.add(device)
        device = DeviceModel("Ayesha", "0770219031", "chamal.bluesmart@gmail.com")
        deviceList.add(device)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        deviceAdapter = DeviceAdapter(deviceList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = deviceAdapter
        Log.d("CARD", deviceAdapter.itemCount.toString())
        deviceAdapter.notifyDataSetChanged()
        deviceAdapter.setOnItemClickListener(object: DeviceAdapter.ClickListener {
            override fun onItemClick(v: View, position: Int) {
                var intent = Intent(this@DeviceListActivity, MainActivity::class.java)
                intent.putExtra("id",position)
                startActivity(intent)
            }
        })
    }
}