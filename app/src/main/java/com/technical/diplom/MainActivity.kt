package com.technical.diplom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import com.google.firebase.database.*
import com.technical.diplom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imageButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                startActivity(Intent(this@MainActivity, LogActivity::class.java))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        database = FirebaseDatabase.getInstance()

        val co2Ref: DatabaseReference = database.getReference("CO2")
        var humRef: DatabaseReference = database.getReference("Humidity")
        var tempRef: DatabaseReference = database.getReference("Temperature")



        co2Ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.co2.setText(snapshot.value.toString())

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        humRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.hum.setText(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        tempRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.temp.setText(snapshot.value.toString() + "°")
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        val coolerRef: DatabaseReference = database.reference.child("control/Cooler")
        val heaterRef: DatabaseReference = database.reference.child("control/Heater")

        binding.cooler.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                coolerRef.setValue(true)
            }else{
                coolerRef.setValue(false)
            }
        }
        binding.heather.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                heaterRef.setValue(true)
            }else{
                heaterRef.setValue(false)
            }
        }
    }
}

