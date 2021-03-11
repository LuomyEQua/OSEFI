package com.androidstrike.osefi.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidstrike.osefi.R
import com.androidstrike.osefi.interfaces.IRecyclerItemClickListener

class SavedHolder (itemView: View) :RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var txtSvdLctName: TextView
    var txtSvdAddress: TextView

    lateinit var iRecyclerItemClickListener: IRecyclerItemClickListener

    fun setClick(iRecyclerItemClickListener: IRecyclerItemClickListener) {
        this.iRecyclerItemClickListener = iRecyclerItemClickListener
    }

    init {
        txtSvdLctName = itemView.findViewById(R.id.txtNameSaved) as TextView
        txtSvdAddress = itemView.findViewById(R.id.txtAddressSaved) as TextView

        itemView.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        iRecyclerItemClickListener.onItemClickListener(v!!, adapterPosition)
    }
}