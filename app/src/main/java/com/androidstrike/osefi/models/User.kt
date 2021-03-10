package com.androidstrike.osefi.models

class User {

    lateinit var name:String
    var email: String? = null
    lateinit var phone: String
    lateinit var date_joined: String

    constructor(){

    }



    constructor(uid: String, email: String?)
    constructor(name: String, email: String?, phone: String, date_joined: String) {
        this.name = name
        this.email = email
        this.phone = phone
        this.date_joined = date_joined
    }

}