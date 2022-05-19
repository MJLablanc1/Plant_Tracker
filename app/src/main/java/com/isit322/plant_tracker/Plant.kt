package com.isit322.plant_tracker

import java.io.Serializable

public class Plant(s: String, s1: String, s2: String) : Serializable {
    lateinit var plantName:String
    lateinit var plantImg:String
    lateinit var location:String
}