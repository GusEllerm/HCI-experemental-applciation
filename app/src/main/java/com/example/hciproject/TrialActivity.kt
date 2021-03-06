package com.example.hciproject

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_trial.*
import android.os.Environment
import android.widget.Button
import android.widget.TextView
import java.io.File


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class TrialActivity : AppCompatActivity() {

    lateinit var FILE_NAME: String
    lateinit var filePath: File
    lateinit var participant: String
    lateinit var membrane: String

    // Button decelerations
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var button5: Button
    lateinit var button6: Button
    lateinit var button7: Button
    lateinit var button8: Button
    lateinit var button9: Button
    lateinit var button10: Button
    lateinit var button11: Button
    lateinit var button12: Button
    lateinit var button13: Button
    lateinit var button14: Button
    lateinit var button15: Button
    lateinit var button16: Button
    lateinit var button17: Button
    lateinit var button18: Button
    lateinit var button19: Button
    lateinit var button20: Button
    lateinit var buttons: Map<Int, Button>
    lateinit var finishScreen: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_trial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        finishScreen = findViewById(R.id.finished)
        finishScreen.visibility = View.GONE

        val intent = intent
        FILE_NAME = intent.getStringExtra("FileName")
        participant = intent.getStringExtra("Participant")
        membrane = intent.getStringExtra("Membrane")

        filePath = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILE_NAME)
//        file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILE_NAME)

        Toast.makeText(this, "$FILE_NAME recording", Toast.LENGTH_SHORT).show()


        button1 = findViewById(R.id.Btn1)
        button2 = findViewById(R.id.Btn2)
        button3 = findViewById(R.id.Btn3)
        button4 = findViewById(R.id.Btn4)
        button5 = findViewById(R.id.Btn5)
        button6 = findViewById(R.id.Btn6)
        button7 = findViewById(R.id.Btn7)
        button8 = findViewById(R.id.Btn8)
        button9 = findViewById(R.id.Btn9)
        button10 = findViewById(R.id.Btn10)
        button11 = findViewById(R.id.Btn11)
        button12 = findViewById(R.id.Btn12)
        button13 = findViewById(R.id.Btn13)
        button14 = findViewById(R.id.Btn14)
        button15 = findViewById(R.id.Btn15)
        button16 = findViewById(R.id.Btn16)
        button17 = findViewById(R.id.Btn17)
        button18 = findViewById(R.id.Btn18)
        button19 = findViewById(R.id.Btn19)
        button20 = findViewById(R.id.Btn20)

        buttons = mapOf(
            1 to button1,
            2 to button2,
            3 to button3,
            4 to button4,
            5 to button5,
            6 to button6,
            7 to button7,
            8 to button8,
            9 to button9,
            10 to button10,
            11 to button11,
            12 to button12,
            13 to button13,
            14 to button14,
            15 to button15,
            16 to button16,
            17 to button17,
            18 to button18,
            19 to button19,
            20 to button20
        )

        buttons.forEach { _, button ->
            button.setOnClickListener {
                writeToFile(targetHit = "false")
            }
        }

        val startTrial = findViewById<Button>(R.id.startTrial)
        startTrial.setOnClickListener {
            startTrial.visibility = View.GONE
            setTouchListner()
            startFirstTrial()
        }

        hideSystemUI()
        hideButtons()
    }

    override fun onResume() {
        super.onResume()
        DONE_TRIALS = 0
    }

    companion object {
        const val NUM_TRIALS = 10
        var DONE_TRIALS = 0
        var CURRENT_BUTTON = 0
        var PREVIOUS_BUTTON = 0
        var START_TIME = System.nanoTime()
    }

    private fun hideButtons() {
        buttons.forEach { _, button ->
            button.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun startFirstTrial() {
        CURRENT_BUTTON = (1..20).random()
        Log.e("CurrentButton", "$CURRENT_BUTTON")

        buttons[CURRENT_BUTTON]?.visibility = View.VISIBLE

        buttons[CURRENT_BUTTON]?.setOnClickListener {
            writeToFile(targetHit = "true")
            nextTrial()
        }
        START_TIME = System.nanoTime()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun nextTrial() {

        DONE_TRIALS += 1

        if (DONE_TRIALS < NUM_TRIALS) {
            this.touchListner.isEnabled = false
            buttons[CURRENT_BUTTON]?.visibility = View.INVISIBLE

            buttons[CURRENT_BUTTON]?.setOnClickListener {
                writeToFile(targetHit = "false")
            }

            // Check that it wont be the same button again
            PREVIOUS_BUTTON = CURRENT_BUTTON
            CURRENT_BUTTON = (1..20).random()
            while (CURRENT_BUTTON == PREVIOUS_BUTTON) {
                CURRENT_BUTTON = (1..20).random()
            }

            Log.e("CurrentButton", "$CURRENT_BUTTON")

            buttons[CURRENT_BUTTON]?.visibility = View.VISIBLE

            buttons[CURRENT_BUTTON]?.setOnClickListener {
                writeToFile(targetHit = "true")
                nextTrial()
            }

            this.touchListner.isEnabled = true
            START_TIME = System.nanoTime()

        } else {
            hideButtons()
            finishScreen.visibility = View.VISIBLE
        }
    }

    private fun writeToFile(targetHit: String) {

        val eventTime = System.nanoTime()
        val inputLine = "$participant,$membrane,$START_TIME,$eventTime,$CURRENT_BUTTON,$targetHit\n"
        filePath.appendText(inputLine)
//        applicationContext.openFileOutput(filePath.absolutePath, Context.MODE_APPEND).use {
//            it.write(inputLine.toByteArray())
//        }
    }

    private fun setTouchListner() {
        this.touchListner.setOnClickListener{
            writeToFile(targetHit = "false")
        }
    }


    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }


    }