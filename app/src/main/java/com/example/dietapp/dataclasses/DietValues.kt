package com.example.dietapp.dataclasses

data class DietValues (
    var id: String = "",
    var name: String = "",
    var calories: Int = 0,
    var timestamp: MutableMap<String, String>
)
