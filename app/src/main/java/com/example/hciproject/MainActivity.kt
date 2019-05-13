package com.example.hciproject

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var FILE_NAME: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startTrialButton = findViewById<Button>(R.id.startTrial)
        startTrialButton.setOnClickListener {
            val intent = Intent(this, TrialActivity::class.java)
            intent.putExtra("FileName", "${selectedParticipant.text}_${membraneSelected.text}.csv")
            intent.putExtra("Participant", "${selectedParticipant.text}")
            intent.putExtra("Membrane", "${membraneSelected.text}")

            startActivity(intent)
        }

        val recordingFileTextView = findViewById<TextView>(R.id.recordingFile)

        val createTrialFile = findViewById<Button>(R.id.makeLogFile)
        createTrialFile.setOnClickListener {
            createTrial()
            recordingFileTextView.text = FILE_NAME
            }

        val participantRadioGroup = findViewById<RadioGroup>(R.id.participantRadioGroup)
        val membraneRadioGroup = findViewById<RadioGroup>(R.id.membraneRadioGroup)

        val selectedParticipant = findViewById<TextView>(R.id.selectedParticipant)
        val selectedMembrane = findViewById<TextView>(R.id.membraneSelected)

        participantRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedParticipant.text = radioButton.text
        }
        membraneRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            selectedMembrane.text = radioButton.text
        }
        checkPermissions()

        val toCalibration = findViewById<Button>(R.id.calibration)
        toCalibration.setOnClickListener{
            startActivity(Intent(this, Calibration::class.java))
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)
            return
        }
    }

    private fun createTrial() {
        if (isExternalStorageWritable()) {
            FILE_NAME = "${selectedParticipant.text}_${membraneSelected.text}.csv"
            getPublicSTorageDir()
        } else {
            Log.e("External Storage", "Storage is not writable")
        }
    }

    private fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    private fun getPublicSTorageDir(): File? {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILE_NAME)
        if (!file.createNewFile()) {
            Log.e("file exists", "trial already exists")
            Toast.makeText(this, "THIS TRIAL ALREADY HAS A FILE - DELETE AND RESTART", Toast.LENGTH_LONG).show()
        } else {
            val inputLine = "participant,membrane,time,x_coord,y_coord,targetButton,targetHit\n"
            file.appendText(inputLine)
        }
        return file
    }
}
