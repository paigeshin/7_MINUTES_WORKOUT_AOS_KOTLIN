[Drawable XML multiple examples](https://www.notion.so/Drawable-XML-multiple-examples-cf15ed77eb0c4a788222baedce79b35b)

# Define dimension

strings, styles, dimens, colors are all the same 

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <dimen name="main_screen_image_height">220dp</dimen>
    <dimen name="main_screen_main_layout_padding">5dp</dimen>
    <dimen name="start_button_size">150dp</dimen>
    <dimen name="start_button_margin_top">20dp</dimen>
    <dimen name="start_button_label_textsize">22sp</dimen>

</resources>
```

# Get rid of ActionBar

```xml
<activity android:name=".MainActivity"
    android:screenOrientation="portrait"
    android:theme="@style/Theme.AppCompat.Light.NoActionBar">
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.a7minutesworkout">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.7MinutesWorkout">
        <activity android:name=".MainActivity"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

# Create custom toolbar

- custom toolbar style, styles.xml (black color)

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>

    <style name="ToolbarTheme" parent="@style/ThemeOverlay.AppCompat.ActionBar">
        <item name="colorControlNormal">@color/toolbar_color_control_normal</item>
    </style>

</resources>
```

- xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".ExerciseActivity">
    
    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbar_exercise_activity"
        android:layout_width="match_parent"
        android:layout_height="?android:attr/actionBarSize"
        android:background="#FFFFFF"
        android:theme="@style/ToolbarTheme"/>

</RelativeLayout>
```

- code

```kotlin
package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        var actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            finish()
        }

    }

}
```

# Kotlin `object` keyword

- Kotlin object Expressions. The object keyword can also be used to create objects of an anonymous class known as anonymous objects.

```kotlin
restTimer = object: CountDownTimer(10000, 1000) {
    override fun onTick(millisUntilFinished: Long) {
        restProgress++
        progressBar.progress = 10 - restProgress
        tvTimer.text = (10 - restProgress).toString()
    }

    override fun onFinish() {
        Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_LONG).show()
    }
}
```

# Set Up Timer

```kotlin
package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    // ** Timer **
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

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
        restTimer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the exercise.", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    // ** Timer **
    private fun setUpRestView() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    override fun onDestroy() {
        // ** Timer **
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

}
```

# Adding The Model

```kotlin
package com.example.a7minutesworkout.model

/**
 * This is used for exercise details it is a custom model class.
 * with getter setter functions and a constructor
 */
// TODO(Step 2 - Making a model class and adding parameters which can be used later as per requirement.)
// START
// Kotlin에서 아래와 같이 선언하면 field value와 생성자를 동시에 만드는 것이라고 생각하면 됨.
class ExerciseModel(
        private var id: Int,
        private var name: String,
        private var image: Int,
        private var isCompleted: Boolean,
        private var isSelected: Boolean
) {

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(image: Int) {
        this.image = image
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(isCompleted: Boolean) {
        this.isCompleted = isCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}
// END
```

# Create Constant File

```kotlin
package com.example.a7minutesworkout.util

import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.model.ExerciseModel
import java.util.*

/**
 * Constant Class where you can add the constant values of the project.
 */
// TODO(Step 3 - Making a default list of exercises in the Constant file along with the name image using the Model Class which later on will be used to show in the UI.)
// START
class Constants {
    companion object {

        // The drawable images used here are added in the drawable folder.
        /**
         * Here we are adding all exercises to a single list with all the default values.
         */
        fun defaultExerciseList(): ArrayList<ExerciseModel> {

            val exerciseList = ArrayList<ExerciseModel>()

            val jumpingJacks =
                    ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
            exerciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.ic_wall_sit, false, false)
            exerciseList.add(wallSit)

            val pushUp = ExerciseModel(3, "Push Up", R.drawable.ic_push_up, false, false)
            exerciseList.add(pushUp)

            val abdominalCrunch =
                    ExerciseModel(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
            exerciseList.add(abdominalCrunch)

            val stepUpOnChair =
                    ExerciseModel(
                            5,
                            "Step-Up onto Chair",
                            R.drawable.ic_step_up_onto_chair,
                            false,
                            false
                    )
            exerciseList.add(stepUpOnChair)

            val squat = ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false)
            exerciseList.add(squat)

            val tricepDipOnChair =
                    ExerciseModel(
                            7,
                            "Tricep Dip On Chair",
                            R.drawable.ic_triceps_dip_on_chair,
                            false,
                            false
                    )
            exerciseList.add(tricepDipOnChair)

            val plank = ExerciseModel(8, "Plank", R.drawable.ic_plank, false, false)
            exerciseList.add(plank)

            val highKneesRunningInPlace =
                    ExerciseModel(
                            9, "High Knees Running In Place",
                            R.drawable.ic_high_knees_running_in_place,
                            false,
                            false
                    )
            exerciseList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(10, "Lunges", R.drawable.ic_lunge, false, false)
            exerciseList.add(lunges)

            val pushupAndRotation =
                    ExerciseModel(
                            11,
                            "Push up and Rotation",
                            R.drawable.ic_push_up_and_rotation,
                            false,
                            false
                    )
            exerciseList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.ic_side_plank, false, false)
            exerciseList.add(sidePlank)

            return exerciseList
        }
    }
}
// END
```

- companion object ⇒ the replacement of static keyword

# ImageView, keep intact ratio, trick

```xml
<ImageView
        android:id="@+id/ivImage"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:contentDescription="@string/image"
        android:scaleType="fitXY"/>
```

# Adding Text to Speech

1. implement `TextToSpeech.OnInitListener`
2. write initialization code with `implemented method`
3. use TTS feature

```kotlin
class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
		
		// ** TTS **
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        // ** TTS **
        tts = TextToSpeech(this, this)

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
```

- Entire code after implementing TTS feature

```kotlin
package com.example.a7minutesworkout.activity

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
```

# Adding A Media Player

### Add Audio Resource

- When you want to use audio resource, create raw directory inside of res.

### Initialization

```kotlin
// ** Media Player **
private var player: MediaPlayer? = null

try {
    player = MediaPlayer.create(applicationContext, R.raw.press_start)
    player!!.isLooping = false
    player!!.start()
} catch (e: Exception) {
    e.printStackTrace()
}
```

# RecyclerView

- RecyclerView can be thought of as a combination of a ListView and a GridView.

### Why Use a RecyclerView?

- Imagine you're creating a **ListView** with complicated custom items.
- You create a row layout for the items and use that layout inside your adapter.
- You inflate your item layout in "**getView()"**, referencing each view with the unique ID you provided in **XML** to customize and add view logic. You pass that to the ListView, and it's ready to be drawn on the screen. Or is it?
- **ListViews** and **GridViews**  only do half the job of achieving true memory efficiency.
- Android initially provided a solution to this problem on the Android Developers site with smooth Scrolling via the power of the "View Holder" pattern.
- With this pattern, a class becomes an in-memory reference to all the views needed to fill your layout.
- You set the references once and reuse them, working around the performance hit that comes with repeatedly calling findViewById().
- Take note: This is an optional pattern for a ListView or GridView. If you're unaware of this detail, then you may wonder why your listVies and GridViews are so slow.

### Why it is called RecyclerView?

- Another perk of using RecyclerViews is that they come from default animations that you don't have to create or add yourself.
- Because it requires a ViewHolder, the RecyclerView knows which animation to apply to which item and adds them as required.

### LayoutManager in RecyclerView

- The last and most interesting component of a RecyclerView it its **LayoutManager.**
- This object positions the RecyclerView's items and tells it when to recycle items that have transitioned off-screen.
- The ListView used to do this work alone.
- The RecyclerView has broken out this functionality to allow for different kinds of layouts: Vertical, Horizontal, Grid, Staggered or your own!

# Preparing RecyclerView

### Prepare for ItemView

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/tvItem"
    android:layout_width="25dp"
    android:layout_height="25dp"
    android:layout_margin="1dp"
    android:gravity="center"
    android:textColor="#212121"
    android:textSize="14sp"
    android:textStyle="bold"
    android:padding="3dp"
    tools:text="1">
</TextView>
```

### Prepare for RecyclerView

```kotlin
package com.example.a7minutesworkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.model.ExerciseModel
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem = view.tvItem
    }

    //return itemView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))
        return view
    }

    //bind data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

    }

    override fun getItemCount(): Int {
        return items.size
    }

}
```

### Initialize RecyclerView

```kotlin
rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
rvExerciseStatus.setAdapter(exerciseAdapter)
```

### Set logic on bindViewHolder

```kotlin
package com.example.a7minutesworkout.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.model.ExerciseModel
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) : RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem = view.tvItem
    }

    //return itemView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false))
        return view
    }

    //bind data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        if(model.getIsSelected()) {
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin_color_accent_border)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        } else if(model.getIsCompleted()){
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
        } else {
            holder.tvItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_gray_background)
            holder.tvItem.setTextColor(Color.parseColor("#212121"))
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

}
```

# drawable xml files, vector

- checkbutton

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="#008F24"
    android:alpha="0.8">
    <path
        android:fillColor="#FF000000"
        android:pathData="M9,16.2L4.8,12l-1.4,1.4L9,19 21,7l-1.4,-1.4L9,16.2z"/>
</vector>
```

# Set Up Custom Dialog

```kotlin
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
```

# Custom ActionBar set title

```kotlin
package com.example.a7minutesworkout.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a7minutesworkout.R
import kotlinx.android.synthetic.main.activity_bmi.*

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

    }
}
```

# TextInputLayout from material component

- TextInputLayout

```xml
<com.google.android.material.textfield.TextInputLayout
    android:id="@+id/tilMetricUnitWeight"
    style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <EditText
        android:id="@+id/etMetricUnitWeight"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="WEIGHT (in kg)"
        android:inputType="numberDecimal"
        android:textSize="16sp" />
</com.google.android.material.textfield.TextInputLayout>
```

- Use MaterialComponents when error occurs using TextInputLayout on Manifest

```xml
<activity
  android:name=".activity.ExerciseActivity"
  android:screenOrientation="portrait"
  android:theme="@style/Theme.MaterialComponents.Light.NoActionBar"/>
```

⇒ Theme.MaterialComponents.Light.NoActionBar 

# BMI - business logic

- you can apply it anywhere

```kotlin
package com.example.a7minutesworkout.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.a7minutesworkout.R
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if(validateMetricUnits()) {
                val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue : Float = etMetricUnitWeight.text.toString().toFloat()

                val bmi = weightValue / (heightValue * heightValue)
                displayBMIResult(bmi)
            }
        }

    }

    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }

    private fun validateMetricUnits() : Boolean {
        var isValid = true
        if(etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if(etMetricUnitHeight.text.toString().isEmpty()) {
            isValid  = false
        }
        return isValid
    }

}
```

# Drawable, Custom radio group & button design

- xml, basic design for radio button group background
- drawable_radio_group_tab_background

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">

    <solid android:color="#ECEDEF"/>
    <stroke android:width="0.5dp"
        android:color="#9AA2AF"/>

    <corners android:radius="30dp"/>

</shape>
```

- xml, drawable, radio button
- drawable_units_tab_selector

```xml
<?xml version="1.0" encoding="utf-8"?>
<!--TODO(Step 3 : Designed a custom selector for the Selected Item of the radio button.)-->
<!--START-->
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:state_checked="true">
        <shape android:shape="rectangle">

            <solid android:color="@color/colorAccent" />

            <corners android:radius="30dp" />
        </shape>
    </item>
</selector>
<!--END-->
```

- xml, drawable, radio button color
- drawable_units_tab_text_color_selector

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:color="#FFFFFF" android:state_checked="true"/>
    <item android:color="#ADB3B3" android:state_checked="false"/>
</selector>
```

- xml in layout

```xml
<RadioGroup
        android:id="@+id/rgUnits"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@id/toolbar_bmi_activity"
        android:layout_margin="10dp"
        android:background="@drawable/drawable_radio_group_tab_background"
        android:orientation="horizontal">

    <RadioButton
        android:id="@+id/rbMetricUnits"
        android:layout_width="0dp"
        android:layout_height="35dp"
        android:layout_weight="0.50"
        android:background="@drawable/drawable_units_tab_selector"
        android:button="@null"
        android:checked="true"
        android:gravity="center"
        android:text="METRIC UNITS"
        android:textColor="@drawable/drawable_units_tab_text_color_selector"
        android:textSize="16sp"
        android:textStyle="bold" />

    <RadioButton
        android:id="@+id/rbUsUnits"
        android:layout_width="0dp"
        android:layout_height="35dp"
        android:layout_weight="0.50"
        android:background="@drawable/drawable_units_tab_selector"
        android:button="@null"
        android:checked="false"
        android:gravity="center"
        android:text="US UNITS"
        android:textColor="@drawable/drawable_units_tab_text_color_selector"
        android:textSize="16sp"
        android:textStyle="bold" />
</RadioGroup>
```

`@null` ⇒ get rid of android default functionality

- create your own logic

```kotlin
//radio button
radioGroupUnits.setOnCheckedChangeListener { group, checkedId ->
    if(checkedId == R.id.rbMetricUnits) {
        makeVisibleMetricUnitsView()
    } else {
        makeVisibleMetricUSUnitsView()
    }
}
```