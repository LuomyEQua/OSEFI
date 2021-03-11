package com.androidstrike.osefi.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidstrike.osefi.R
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener

class LocateHolder (itemView: View) :RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var txtLctName: TextView
    var txtLctAddress: TextView

    lateinit var iRecyclerItemClickListener: IRecyclerItemClickListener

    fun setClick(iRecyclerItemClickListener: IRecyclerItemClickListener) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener
    }

    init {
        txtLctName = itemView.findViewById(R.id.txtNameLocate) as TextView
        txtLctAddress = itemView.findViewById(R.id.txtAddressLocate) as TextView

        itemView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        iRecyclerItemClickListener.onItemClickListener(v!!, adapterPosition)
    }
}