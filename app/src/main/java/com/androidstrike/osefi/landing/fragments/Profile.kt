package com.androidstrike.osefi.landing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidstrike.osefi.R
import com.androidstrike.osefi.models.User
import com.androidstrike.osefi.utils.Common
import com.androidstrike.osefi.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : Fragment() {

    lateinit var database: FirebaseDatabase
    lateinit var query: DatabaseReference

    private var mAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null

    var usrModel: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // here we initialize the instance of the Firebase Auth
        mAuth = FirebaseAuth.getInstance()
        firebaseUser = mAuth!!.currentUser

        database = FirebaseDatabase.getInstance()
        query = database.getReference("Users/${firebaseUser?.uid}")

        val profileListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usrModel = snapshot.getValue(User::class.java)
                profile_name.text = usrModel!!.name
                profile_email.text = usrModel!!.email
                profile_phone.text = usrModel!!.phone
                profile_date_joined.append("Date Joined: ${usrModel!!.date_joined}")

            }

            override fun onCancelled(error: DatabaseError) {
                activity?.toast(error.message)
            }

        }
        query.addListenerForSingleValueEvent(profileListener)

    }
}