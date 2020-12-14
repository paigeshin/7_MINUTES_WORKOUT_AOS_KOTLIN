package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    // ** Timer **
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10

    // ** Exercise Timer **
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // ** SupportActionBar **
        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            finish()
        }

        // ** Timer **
        setUpRestView()

    }
    // ** Timer **
    private fun setRestProgressBar() {
        progressBar.progress = restProgress
        restTimer = object: CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restTimerDuration.toInt() - restProgress
                tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_LONG).show()
                setUpExerciseView()
            }
        }.start()
    }

    // ** Timer **
    private fun setUpRestView() {

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    // ** Exercise Timer **
    private fun setExerciseProgressBar() {
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the next rest screen.", Toast.LENGTH_LONG).show()
                setUpRestView()
            }
        }.start()
    }

    // ** Exercise Timer **
    private fun setUpExerciseView() {

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }



    override fun onDestroy() {
        // ** Timer **
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        // ** Exercise Timer **
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        super.onDestroy()
    }


}

