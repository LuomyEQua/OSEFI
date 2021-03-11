package com.androidstrike.osefi.models

class Saved {

    lateinit var providerId: String
    lateinit var providerName: String
    lateinit var providerLocation: String

    constructor()
    constructor(providerId: String, providerName: String, providerLocation: String) {
        this.providerId = providerId
        this.providerName = providerName
        this.providerLocation = providerLocation
    }


}