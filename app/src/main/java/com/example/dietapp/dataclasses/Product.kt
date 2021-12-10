package com.example.dietapp.dataclasses

data class Product(
    var id: String = "",
    var name: String = "",
    var calories: Int = 0,
    var carbs: Int = 0,
    var fats: Int = 0,
    var proteins: Int = 0,
    var weight: Int = 0,
    var timestamp: MutableMap<String, String>
)