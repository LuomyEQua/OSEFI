package com.androidstrike.osefi.landing.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidstrike.osefi.R
import com.androidstrike.osefi.fragments.Locate
import com.androidstrike.osefi.fragments.search.ByLocation
import com.androidstrike.osefi.fragments.search.ByProviderName
import com.androidstrike.osefi.utils.toast
import kotlinx.android.synthetic.main.fragment_search.*

class Search : Fragment() {

    lateinit var selectedMethod: String

    val REQUEST_SERVICE_NAME = 100
    val REQUEST_PROVIDER_LOCATION = 200
    val REQUEST_PROVIDER_NAME = 300

    var REQUEST_RESULT: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showMethodChooseAlert()
    }

    var checkedItem = -1
    var checkedService = -1

    private fun showMethodChooseAlert() {
        val methods = arrayOf("Service Name", "Location", "Provider Name")

        lateinit var message: String

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Search by ...")
        builder.setSingleChoiceItems(
            methods,
            checkedItem,
            DialogInterface.OnClickListener { dialog, which ->
                //user checked item
                checkedItem = which
                for (method in methods){
                    when(checkedItem){
                        0 ->{
                            REQUEST_RESULT = REQUEST_SERVICE_NAME
                            message = "Service Name Selected"
                            activity?.toast(message)
                            txtsearch.text = message
                        }

                        1 ->{
                            REQUEST_RESULT = REQUEST_PROVIDER_LOCATION
                            message = "Location Selected"
                            activity?.toast(message)
                            txtsearch.text = message
                        }

                        2 ->{
                            REQUEST_RESULT = REQUEST_PROVIDER_NAME
                            message = "Provider Name Selected"
                            activity?.toast(message)
                            txtsearch.text = message
                        }




                    }
                }
            }
        )
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, which ->

            when(REQUEST_RESULT){
                REQUEST_SERVICE_NAME -> {
                    activity?.toast(REQUEST_RESULT.toString())
                    val services = arrayOf("Carpenter", "Painter", "Electrician", "Mechanic", "Plumber", "Tailor" )

                    lateinit var selectedService: String

                    val serviceBuilder = AlertDialog.Builder(context)
                    serviceBuilder.setTitle("Search for ...")
                    serviceBuilder.setSingleChoiceItems(
                        services,
                        checkedService,
                        DialogInterface.OnClickListener { dialog, which ->
                            checkedService = which
                            for (service in services){
                                when(checkedService){
                                    0 -> {
                                        //Carpenter
                                        selectedService = "Carpenter"

                                        val marketName = selectedService
                                        val marketId = "carpenter01"

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
                                    1 -> {
                                        //Painter
                                        selectedService = "Painter"


                                        val marketName = selectedService
                                        val marketId = "painter01"

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
                                    2 -> {
                                        //Electrician
                                        selectedService = "Electrician"


                                        val marketName = selectedService
                                        val marketId = "electrician01"

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
                                    3 -> {
                                        //Mechanic
                                        selectedService = "Mechanic"


                                        val marketName = selectedService
                                        val marketId = "mechanic01"

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
                                    4 -> {
                                        //Plumber
                                        selectedService = "Plumber"


                                        val marketName = selectedService
                                        val marketId = "plumber01"

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
                                    5 -> {
                                        //Tailor
                                        selectedService = "Tailor"


                                        val marketName = selectedService
                                        val marketId = "tailor01"

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
                            }
                        }
                    )
                    serviceBuilder.setPositiveButton("Search", null)
                    serviceBuilder.show()
                }
                REQUEST_PROVIDER_LOCATION -> {
                    activity?.toast(REQUEST_RESULT.toString())

                    val frag_artisans = ByLocation()

                    val manager = fragmentManager

                    val frag_transaction = manager?.beginTransaction()

                    frag_transaction?.replace(R.id.fragment_container, frag_artisans)
                    frag_transaction?.commit()

                }
                REQUEST_PROVIDER_NAME -> {
                    activity?.toast(REQUEST_RESULT.toString())

                    val frag_artisans = ByProviderName()

                    val manager = fragmentManager

                    val frag_transaction = manager?.beginTransaction()

                    frag_transaction?.replace(R.id.fragment_container, frag_artisans)
                    frag_transaction?.commit()
                }
            }
        })
        builder.setNegativeButton("Cancel", null)

        builder.show()

    }
}