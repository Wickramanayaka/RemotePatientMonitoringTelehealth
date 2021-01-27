package com.example.medicaldevice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

 class DeviceAdapter(private var deviceList: List<DeviceModel>): RecyclerView.Adapter<DeviceAdapter.MyViewHolder>() {
     private var clickListener: ClickListener? = null

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        var name: TextView = view.findViewById(R.id.textViewName)
        var telephone: TextView = view.findViewById(R.id.textViewTelephone)
        var email: TextView = view.findViewById(R.id.textViewEmail)
        var photo: ImageView = view.findViewById(R.id.imageViewDevice)
        init{
            if(clickListener != null){
                itemView.setOnClickListener(this)
            }
        }

        override fun onClick(v: View?) {
            if(v != null){
                clickListener?.onItemClick(v, adapterPosition)
            }
        }
    }
     interface  ClickListener{
         fun onItemClick(v: View, position: Int)
     }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.device_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val device = deviceList[position]
        holder.name.text =  device.getName()
        holder.telephone.text = device.getTelephone()
        holder.email.text = device.getEmail()
        if(position==0){
            holder.photo.setImageResource(R.drawable.avatar)
        }
        else{
            holder.photo.setImageResource(R.drawable.user)
        }


    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

     fun setOnItemClickListener(clickListener: ClickListener){
         this.clickListener = clickListener
     }
}