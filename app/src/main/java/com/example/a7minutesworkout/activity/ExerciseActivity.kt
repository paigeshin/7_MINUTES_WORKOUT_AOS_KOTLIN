package com.example.a7minutesworkout.activity

import android.app.Dialog
import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.adapter.ExerciseStatusAdapter
import com.example.a7minutesworkout.model.ExerciseModel
import com.example.a7minutesworkout.util.Constants
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

// ** TTS **
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // ** Timer **
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration: Long = 10 //10

    // ** Exercise Timer **
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30 //30

    // ** Use Exercise Data **
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    // ** TTS **
    private var tts: TextToSpeech? = null

    // ** Media Player **
    private var player: MediaPlayer? = null

    // ** RecyclerView **
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        // ** SupportActionBar **
        setSupportActionBar(toolbar_exercise_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        // ** TTS **
        tts = TextToSpeech(this, this)

        // ** Use Exercise Data **
        exerciseList = Constants.defaultExerciseList()

        // ** Timer **
        setUpRestView()

        // ** RecyclerView **
        setUpExerciseStatusRecyclerView()

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

                //selected
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter?.notifyDataSetChanged()

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

                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseAdapter?.notifyDataSetChanged()

                    setUpRestView()

                } else {

                    // All exercises are done, go to another scene
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)

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

    // ** RecyclerView **
    private fun setUpExerciseStatusRecyclerView() {
        rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.setAdapter(exerciseAdapter)
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

}

