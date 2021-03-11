package com.androidstrike.osefi.models

class Markets {

    var name: String? = null
    var image: String? = null
    var id: String? = null

    constructor()
    constructor(name: String?, image: String?, id: String?) {
        this.name = name
        this.image = image
        this.id = id
    }

}