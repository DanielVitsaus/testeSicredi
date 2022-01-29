package com.example.domain.entity.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val image: String,
    val date: LocalDateTime,
    val latitude: Double,
    val longitude: Double,
    val price: Double
) : Parcelable
