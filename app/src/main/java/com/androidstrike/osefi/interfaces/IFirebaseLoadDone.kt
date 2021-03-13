package com.androidstrike.osefi.interfaces

interface IFirebaseLoadDone {
    fun onFirebaseUserDone (lstEmail:List<String>)
    fun onFirebaseLoadFailed(message: String)
}