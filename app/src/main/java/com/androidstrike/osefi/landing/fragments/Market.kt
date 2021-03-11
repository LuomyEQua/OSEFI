package com.androidstrike.osefi.landing.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.androidstrike.osefi.R
import com.androidstrike.osefi.adapters.MarketHolder
import com.androidstrike.osefi.fragments.Locate
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener
import com.androidstrike.osefi.models.Markets
import com.androidstrike.osefi.utils.GridItemDecoration
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_market.*
import java.lang.StringBuilder
import java.lang.System.load

class Market : Fragment() {

    var adapter: FirebaseRecyclerAdapter<Markets, MarketHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        rv_mkt.layoutManager = GridLayoutManager(activity, 2)
        rv_mkt.addItemDecoration(GridItemDecoration(10, 2))

        loadMarket()

    }

    private fun loadMarket() {

        val query = FirebaseDatabase.getInstance().getReference("Services")

        val options = FirebaseRecyclerOptions.Builder<Markets>()
            .setQuery(query, Markets::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Markets, MarketHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_market, parent, false)

                return MarketHolder(itemView)
            }

            override fun onBindViewHolder(holder: MarketHolder, position: Int, model: Markets) {
                holder.txtMktTitle.text = StringBuilder(model.name!!)

                Picasso.get().load(model.image)
                    .into(holder.ivMktImage)

                holder.setClick(object : IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {


                        openArtisanList(model)
                    }

                })
            }

        }

        adapter!!.startListening()
        rv_mkt.adapter = adapter
    }

    private fun openArtisanList(model: Markets) {
        //"Make the service Id in each provider the same with that in services"

        val marketName = model.name
        val marketId = model.id


        val frag_artisans = Locate()

        val bundle = Bundle()
        bundle.putString("marketName", marketName)
        bundle.putString("marketId", marketId)
        frag_artisans.arguments = bundle

        val manager = fragmentManager

        val frag_transaction = manager?.beginTransaction()

        frag_transaction?.replace(R.id.fragment_container, frag_artisans)
        frag_transaction?.commit()
    }
}