package com.example.dietapp.dataclasses

data class AdminProduct(
    var name: String = "",
    var calories: Int = 0,
    var carbs: Int = 0,
    var fats: Int = 0,
    var proteins: Int = 0,
    var timestamp: MutableMap<String, String>
)