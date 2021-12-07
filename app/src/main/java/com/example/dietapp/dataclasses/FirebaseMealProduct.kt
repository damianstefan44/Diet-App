package com.example.dietapp.dataclasses

data class FirebaseMealProduct (
    var name: String = "",
    var calories: Int = 0,
    var carbs: Int = 0,
    var eaten: Boolean = false,
    var fats: Int = 0,
    var proteins: Int = 0,
    var weight: Int = 0
)
