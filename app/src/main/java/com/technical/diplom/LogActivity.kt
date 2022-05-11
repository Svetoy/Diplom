package com.technical.diplom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.technical.diplom.databinding.ActivityLogBinding


private lateinit var binding: ActivityLogBinding

private lateinit var dbRef: DatabaseReference
private lateinit var recycler : RecyclerView
private lateinit var arrayLog : ArrayList<LogList>

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        arrayLog = arrayListOf<LogList>()
        getLog()
    }

    private fun getLog() {

        dbRef = FirebaseDatabase.getInstance().getReference("log")

        dbRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

//                System.out.println(snapshot.toString())

                if(snapshot.exists()){
                    for(logSnapshot in snapshot.children){

                        var logi = logSnapshot.getValue(LogList::class.java)
                        arrayLog.add(logi!!)

//                        System.out.println(logSnapshot.key.toString())
                    }

                    recycler.adapter = MyAdapter(arrayLog)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}

