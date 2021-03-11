package com.androidstrike.osefi.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidstrike.osefi.R
import com.androidstrike.osefi.adapters.LocateHolder
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener
import com.androidstrike.osefi.models.Locates
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_locate.*
import java.lang.StringBuilder

class Locate : Fragment() {

    var mMarketName: String? = null
    var mMarketId: String? = null

    var artisanId: String? = null

    var adapter: FirebaseRecyclerAdapter<Locates, LocateHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_locate, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments?.getString("marketId") != null){
            mMarketId = arguments?.getString("marketId")!!
            mMarketName = arguments?.getString("marketName")
        }

        val layoutManager = LinearLayoutManager(activity)
        rv_locate.layoutManager = layoutManager
        rv_locate.addItemDecoration(DividerItemDecoration(activity, layoutManager.orientation))

        loadArtisans()

    }

    private fun loadArtisans() {
        val query = FirebaseDatabase.getInstance().getReference("Services/$mMarketName/providers")

        val options = FirebaseRecyclerOptions.Builder<Locates>()
            .setQuery(query, Locates::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Locates, LocateHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocateHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_locate, parent,false)

                return LocateHolder(itemView)
            }

            override fun onBindViewHolder(holder: LocateHolder, position: Int, model: Locates) {

                holder.txtLctName.text = StringBuilder(model.name)
                holder.txtLctAddress.text = StringBuilder(model.location)
                artisanId = model.id


                holder.setClick(object : IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {
                        openArtisanPerson(model)
                    }

                })

            }

        }

        adapter!!.startListening()
        rv_locate.adapter = adapter
    }

    private fun openArtisanPerson(model: Locates) {

        val artisanId = model.id



        val frag_art = Artisan()

        val bundle = Bundle()
        bundle.putString("artisanId", artisanId)
        frag_art.arguments = bundle

        val manager = fragmentManager
        val frag_transaction = manager?.beginTransaction()
        frag_transaction?.replace(R.id.fragment_container, frag_art)
        frag_transaction?.commit()
    }
}