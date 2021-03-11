package com.androidstrike.osefi.landing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidstrike.osefi.R
import com.androidstrike.osefi.adapters.SavedHolder
import com.androidstrike.osefi.fragments.Artisan
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener
import com.androidstrike.osefi.models.Saved
import com.androidstrike.osefi.models.User
import com.androidstrike.osefi.utils.Common
import com.androidstrike.osefi.utils.toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.lang.StringBuilder

class Profile : Fragment() {

    lateinit var database: FirebaseDatabase
    lateinit var query: DatabaseReference
    lateinit var querySaved: DatabaseReference

    private var mAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null

    var usrModel: User? = null
//    var artisanId: String? = null

    var adapter: FirebaseRecyclerAdapter<Saved, SavedHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(activity)
        profile_recycler.layoutManager = layoutManager
        profile_recycler.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))

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

        querySaved = database.getReference("Users/${firebaseUser?.uid}/saved")

        val options = FirebaseRecyclerOptions.Builder<Saved>()
            .setQuery(querySaved, Saved::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Saved, SavedHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_saved, parent, false)

                return SavedHolder(itemView)
            }

            override fun onBindViewHolder(holder: SavedHolder, position: Int, model: Saved) {

                holder.txtSvdLctName.text = StringBuilder(model.providerName)
                holder.txtSvdAddress.text = StringBuilder(model.providerLocation)
                val artisanId = model.providerId
                val artisanName = model.providerName
                val artisanLocation = model.providerLocation


                holder.setClick(object : IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {

                        val frag_art = Artisan()

                        val bundle = Bundle()
                        bundle.putString("artisanId", artisanId)
                        bundle.putString("artisanName", artisanName)
                        bundle.putString("artisanLocation", artisanLocation)
                        frag_art.arguments = bundle

                        val manager = fragmentManager
                        val frag_transaction = manager?.beginTransaction()
                        frag_transaction?.replace(R.id.fragment_container, frag_art)
                        frag_transaction?.commit()
                    }

                })
            }

        }

        adapter!!.startListening()
        profile_recycler.adapter = adapter
        query.addListenerForSingleValueEvent(profileListener)

    }
}