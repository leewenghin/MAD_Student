package com.example.project

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext


class MyAdapter(private val submissionList: ArrayList<Submission>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(){

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val label: TextView = itemView.findViewById(R.id.icon_title)
        val sub_date: TextView = itemView.findViewById(R.id.subDate)
        val Status: TextView = itemView.findViewById(R.id.status)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.label.text = submissionList[position].label
        holder.sub_date.text = submissionList[position].date_created

        when (holder.Status.text){
            "" -> {
                holder.Status.setBackgroundColor(Color.TRANSPARENT)
                //                holder.Status.setBackgroundColor(Color.parseColor("#e7eecc"))
                holder.cardView.setBackgroundResource(R.color.grey);
                holder.Status.setText("")
            }
        }

    }

    override fun getItemCount(): Int {
        return submissionList.size
    }
}