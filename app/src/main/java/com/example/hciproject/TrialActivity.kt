package com.example.hciproject

import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.support.v4.app.NavUtils
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_trial.*
import android.os.CountDownTimer
import android.view.MotionEvent
import android.widget.Button
import kotlinx.android.synthetic.main.activity_trial.view.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class TrialActivity : AppCompatActivity() {

    lateinit var FILE_NAME: String

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_trial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        FILE_NAME = intent.getStringExtra("FileName")
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

        button1.setOnTouchListener(View.OnTouchListener{ _, event ->
            val x = event.x
            val y = event.y

            Log.d("Action", "ACTION_DOWN \n" +
                    "x: $x\n" +
                    "y: $y\n " +
                    "TargetHit: true")

            return@OnTouchListener  true
        })



        hideSystemUI()
        initWaitPeriod()

    }

    private fun setTouchListner() {
        this.touchListner.setOnTouchListener(View.OnTouchListener { _, event ->
            val x = event.x
            val y = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Action", "ACTION_DOWN \n" +
                            "x: $x\n" +
                            "y: $y\n " +
                            "TargetHit: false")
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("Action", "ACTION_MOVE \n" +
                            "x: $x\n" +
                            "y: $y\n" +
                            " TargetHit: false")
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("Action", "ACTION_UP \n" +
                            "x: $x\n" +
                            "y: $y\n" +
                            " TargetHit: false")
                }
            }
            return@OnTouchListener  true
        })
    }


    private fun initWaitPeriod() {
        object : CountDownTimer(20000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Toast.makeText(applicationContext, "seconds remaining: " + millisUntilFinished / 1000, Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                Toast.makeText(applicationContext, "Start", Toast.LENGTH_SHORT).show()
                setTouchListner()
            }
        }.start()
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