package com.androidstrike.osefi.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidstrike.osefi.R
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener

class MarketHolder (itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var txtMktTitle: TextView
    var ivMktImage: ImageView

    lateinit var iRecyclerItemClickListener: IRecyclerItemClickListener

    fun setClick(iRecyclerItemClickListener: IRecyclerItemClickListener) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener
    }

    init {
        txtMktTitle = itemView.findViewById(R.id.mkt_title) as TextView
        ivMktImage = itemView.findViewById(R.id.mkt_img) as ImageView

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        iRecyclerItemClickListener.onItemClickListener(v!!, adapterPosition)
    }
}