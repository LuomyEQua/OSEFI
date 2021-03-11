package com.androidstrike.osefi.models

class Artisans {

    lateinit var email: String
    lateinit var experience: String
    lateinit var id: String
    lateinit var location: String
    lateinit var name: String
    lateinit var phone: String

    constructor()
    constructor(
        email: String,
        experience: String,
        id: String,
        location: String,
        name: String,
        phone: String
    ) {
        this.email = email
        this.experience = experience
        this.id = id
        this.location = location
        this.name = name
        this.phone = phone
    }


}