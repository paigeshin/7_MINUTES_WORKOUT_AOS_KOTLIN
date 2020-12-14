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