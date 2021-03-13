package com.androidstrike.osefi.fragments.search

import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidstrike.osefi.R
import com.androidstrike.osefi.adapters.LocateHolder
import com.androidstrike.osefi.fragments.Artisan
import com.androidstrike.osefi.interfaces.IFirebaseLoadDone
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener
import com.androidstrike.osefi.models.Locates
import com.androidstrike.osefi.utils.toast
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.fragment_by_provider_name.*

class ByProviderName : Fragment(), IFirebaseLoadDone {

    var adapter: FirebaseRecyclerAdapter<Locates, LocateHolder>? = null
    var searchAdapter: FirebaseRecyclerAdapter<Locates, LocateHolder>? = null

    lateinit var database: FirebaseDatabase
    lateinit var providerList: DatabaseReference

    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    var suggestList: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_by_provider_name, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        database = FirebaseDatabase.getInstance()
        providerList = database.getReference("Providers")

        //Init View
        //For search bar
        loadSuggest()
        material_search_bar_requests.lastSuggestions = suggestList
        material_search_bar_requests.setCardViewElevation(5)
        material_search_bar_requests.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var suggest = ArrayList<String>()
                for (search in suggestList){
                    if (search.toLowerCase().contentEquals(material_search_bar_requests.text.toLowerCase()))
                        suggest.add(search)
                }
                material_search_bar_requests.lastSuggestions = suggest

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        material_search_bar_requests.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                if (!enabled){
                    if (adapter != null)
                        recycler_all_people.adapter = adapter
                }
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                startSearch(text.toString())
                material_search_bar_requests.clearSuggestions()
            }

            override fun onButtonClicked(buttonCode: Int) {

            }

        })


        recycler_all_people.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        recycler_all_people.layoutManager = layoutManager
        recycler_all_people.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )

        iFirebaseLoadDone = this

        loadUserList()
        loadSearchData()

    }

    private fun loadSuggest() {
        providerList.orderByChild("name")
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children){
                        val item = postSnapshot.getValue(Locates::class.java)
                        suggestList.add(item?.name!!)
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }

    private fun startSearch(search_string: String) {

        val query = FirebaseDatabase.getInstance()
            .getReference("Providers") //begin to loop through the database of the service providers
            .orderByChild("name") // and sort the name of the providers
            .startAt(search_string) //starting from the searched providers

        val options =
            FirebaseRecyclerOptions.Builder<Locates>() //set the search based on the details in the model class
                .setQuery(query, Locates::class.java)
                .build()

        //build and define the search adapter
        searchAdapter = object : FirebaseRecyclerAdapter<Locates, LocateHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocateHolder {
                //inflate the custom layout
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_locate, parent, false)
                return LocateHolder(itemView)
            }

            override fun onBindViewHolder(
                //bind the text data
                holder: LocateHolder,
                position: Int,
                model: Locates
            ) {

                holder.txtLctName.text = StringBuilder(model.name)
//                holder.txtLctAddress.text = StringBuilder(model.location!!)

                //Event
                holder.setClick(object : IRecyclerItemClickListener {
                    override fun onItemClickListener(view: View, position: Int) {
                        val artisanId = model.id
                        val artisanName = model.name
                        val artisanLocation = model.location


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
//
            }

        }

        searchAdapter!!.startListening()
        recycler_all_people.adapter = searchAdapter
    }

    private fun loadSearchData() { // function to load searched individual data from cloud (firebase)
        val lstUserEmail = ArrayList<String>()
        val userList = FirebaseDatabase.getInstance().getReference("Providers")
        userList.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(Locates::class.java)!!
                    lstUserEmail.add(user!!.name!!)
                }
                iFirebaseLoadDone.onFirebaseUserDone(lstUserEmail)
            }

        })
    }

    private fun loadUserList() { //function to load the list of searched users' detail
        val query = FirebaseDatabase.getInstance().getReference("Providers")

        val options = FirebaseRecyclerOptions.Builder<Locates>()
            .setQuery(query, Locates::class.java)
            .build()

        adapter = object : FirebaseRecyclerAdapter<Locates, LocateHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocateHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_locate,parent,false)
                return LocateHolder(itemView)
            }

            override fun onBindViewHolder(holder: LocateHolder, position: Int, model: Locates) {
                holder.txtLctName.text = StringBuilder(model.name)
                holder.txtLctAddress.text = StringBuilder(model.location)

                holder.setClick(object : IRecyclerItemClickListener{
                    override fun onItemClickListener(view: View, position: Int) {
                        val artisanId = model.id
                        val artisanName = model.name
                        val artisanLocation = model.location


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
        recycler_all_people.adapter = adapter
    }


    override fun onStop() { //onStop, stop all listeners
        if (adapter != null)
            adapter!!.stopListening()
        if (searchAdapter != null)
            searchAdapter!!.stopListening()

        super.onStop()
    }

    override fun onFirebaseUserDone(lstEmail: List<String>) {
        material_search_bar_requests.lastSuggestions = lstEmail
    }

    override fun onFirebaseLoadFailed(message: String) {
        activity?.toast(message)
    }
}