package com.example.a7minutesworkout.activity

import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.model.ExerciseModel
import com.example.a7minutesworkout.util.Constants
import kotlinx.android.synthetic.main.activity_exercise.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

// ** TTS **
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // ** Timer **
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10

    // ** Exercise Timer **
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    // ** Use Exercise Data **
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    // ** TTS **
    private var tts: TextToSpeech? = null

    // ** Media Player **
    private var player: MediaPlayer? = null


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

        // ** TTS **
        tts = TextToSpeech(this, this)

        // ** Use Exercise Data **
        exerciseList = Constants.defaultExerciseList()

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

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onFinish() {
                currentExercisePosition++ //increment exercise data position whenever each exercise session is finished
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_LONG).show()
                setUpExerciseView()
            }
        }.start()
    }

    // ** Timer **
    private fun setUpRestView() {

        // ** Media Player **
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseList != null) {
            tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()
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

                if(currentExercisePosition < exerciseList?.size!! - 1) {
                    setUpRestView()
                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations! You have completed the 7 minutes workout.", Toast.LENGTH_SHORT).show()
                }

            }
        }.start()
    }

    // ** Exercise Timer **
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExerciseView() {

        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()

        // ** TTS **
        speakOut(exerciseList!![currentExercisePosition].getName())

        if(exerciseList != null) {
            ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
            tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
        }

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
        // ** TTS **
        if(tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        // ** player **
        if(player != null) {
            player!!.stop()
        }
        super.onDestroy()
    }

    // ** TTS **
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US) //created from `onCreate`
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        }
    }

    // ** TTS **
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

}

