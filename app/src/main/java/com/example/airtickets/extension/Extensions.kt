package com.example.airtickets.extension

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.toDate(): String {
    val dateTime: ZonedDateTime = OffsetDateTime.parse(this).toZonedDateTime()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    return dateTime.format(formatter)
}

fun String.toDateEndTime(): String {
    val dateTime: ZonedDateTime = OffsetDateTime.parse(this).toZonedDateTime()
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
    return dateTime.format(formatter)
}

fun View.anim(view: View) {
    ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0F, 1.2F, 1.0F),
        PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0F, 1.2F, 1.0F)
    ).start()
}