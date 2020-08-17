package com.example.smrlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sample data
        val username = "Jimmy"
        val ecgData1 = listOf(0.0, 0.0, 0.0, -1, -0.15, -0.15, 0.25, -0.25, - 0.35, 1.5, -0.8, -0.1, 0.1, 0.2, 0.25, 0.3, 0.4, 0.1, 0, -0.1, -0.1 , 0.0)
        val ecgData2 = listOf(0.0, 0.3, 0.2, 0.1, 0.15, 0.0, 0.15, 0.15, - 0.35, 1.5, -0.6, 0.0, 0.1, 0.2, 0.1, 0.25, 0.2, 0.45, 0.5, -0.2, -0.1 , 0.0)
        val ecgData3 = listOf(0.0, 0.1, 0.2, 0.1, -0.15, 0.0, 0.15, 0.25, 0.35, 1.5, -1.2, -0.2, 0.1, 0.1, 0.15, 0.25, 0.1, 0.0, 0.0, -0.2, -0.4 , 0.0)
        val ecgData4 = listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        val ecgSampleDataList = listOf(ecgData1, ecgData2, ecgData3, ecgData4)

        val start = findViewById<Button>(R.id.start)

        val ref = FirebaseDatabase.getInstance().getReference("profile")
        val profileId = ref.push().key as String

        //execute code block when start button is pressed
        start.setOnClickListener {

            object : CountDownTimer(3000, 500){
                override fun onFinish() {
                    println("finish")
                }
                override fun onTick(p0: Long) {
                    //generate random data each 500ms to simulate input from BLE device
                    var data = ecgSampleDataList.shuffled().take(1)[0]




                    val profile = Profile(profileId, username, data as List<Double>)
                    println(profile.username)

                    ref.child(profileId).setValue(profile).addOnCompleteListener {
                        println("successful push")
                        println(data)
                    }
                }
            }.start()
        }




    }
}
