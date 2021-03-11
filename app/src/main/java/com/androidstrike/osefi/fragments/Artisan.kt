package com.androidstrike.osefi.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.androidstrike.osefi.R
import com.androidstrike.osefi.models.Artisans
import com.androidstrike.osefi.utils.toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_artisan.*

class Artisan : Fragment() {

    lateinit var database: FirebaseDatabase
    lateinit var query: DatabaseReference

    var artModel: Artisans? =null

    var aArtisanId: String? = null

    var saved: Boolean = false

    var REQUEST_PHONE_CALL= 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artisan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.getString("artisanId") != null){
            aArtisanId = arguments?.getString("artisanId")!!
        }

        database = FirebaseDatabase.getInstance()
        query = database.getReference("Providers/$aArtisanId")

        val artisanListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                artModel = snapshot.getValue(Artisans::class.java)
                artisan_name.text = artModel!!.name
                artisan_email.text = artModel!!.email
                artisan_phone.text = artModel!!.phone
                artisan_xp.append("${artModel!!.experience} experience")
                artisan_location.text = artModel!!.location

                setClicks()
            }

            override fun onCancelled(error: DatabaseError) {
                activity?.toast(error.message)
            }

        }
        query.addListenerForSingleValueEvent(artisanListener)
    }

    private fun setClicks() {
        artisan_email.setOnClickListener {
            val emailAddress = artisan_email.text.toString()
            val artisanName = artisan_name.text.toString()

            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailAddress)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OSEFI")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, $artisanName")

            emailIntent.type = "message/rfc822"

            startActivity(Intent.createChooser(emailIntent, "Send Email via: "))
//            activity?.toast(emailAddress)
        }

        artisan_phone.setOnClickListener {

            val phoneNumber = artisan_phone.text.toString()

            val dialIntent = Intent(Intent.ACTION_CALL)
            dialIntent.data = Uri.fromParts("tel",phoneNumber,null)
            startActivity(dialIntent)
//            activity?.toast(phoneNumber)
        }

        artisan_location.setOnClickListener {
            val locationMap = artisan_location.text.toString()

            val builder = Uri.Builder()
            builder.scheme("geo")
                .path("0,0")
                .query(locationMap)

            val addressUri = builder.build()

            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=$locationMap") )//addressUri)

            if (mapIntent.resolveActivity(activity?.packageManager!!) !=null){
                startActivity(mapIntent)
            }
//            activity?.toast(locationMap)
        }

        artisan_save.setOnClickListener {
            if (!saved){
                artisan_save.setImageResource(R.drawable.ic_baseline_yes_bookmark_24)
                saved = true
                activity?.toast("Saved")
            }
            else if (saved){
                artisan_save.setImageResource(R.drawable.ic_baseline_no_bookmark_24)
                saved = false
                activity?.toast("Not Saved")
            }

        }
    }
}