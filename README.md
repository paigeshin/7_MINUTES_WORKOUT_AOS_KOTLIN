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

# Create custom drawable xml

- item_circular_color_accent_border.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="oval">

    <stroke
        android:width="5dp"
        android:color="@color/purple_200"/>

    <solid
        android:color="@color/colorWhite"/>

</shape>
```

- item_color_accent_border_ripple_background.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/colorHighlighted">
    <item android:id="@+id/mask">
        <shape android:shape="oval">
            <solid android:color="#000000"/>
        </shape>
    </item>
    <item android:drawable="@drawable/item_circular_color_accent_border"/>
</ripple>
```