package com.androidstrike.osefi.models

class Locates {

    lateinit var id: String
    lateinit var location: String
    lateinit var name: String

    constructor()
    constructor(id: String, location: String, name: String) {
        this.id = id
        this.location = location
        this.name = name
    }


}