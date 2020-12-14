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