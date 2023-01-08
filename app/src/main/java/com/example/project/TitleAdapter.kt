package com.example.project

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.withContext
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class TitleAdapter(private val submissionList: ArrayList<Submission>) :
    RecyclerView.Adapter<TitleAdapter.MyViewHolder>(){

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

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.label.text = submissionList[position].label
        holder.sub_date.text = submissionList[position].deadline
        holder.Status.text = submissionList[position].status
        val submissionId = submissionList[position].id

        holder.cardView.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(view.context, submissionId + holder.sub_date.text, Toast.LENGTH_LONG).show()
            val intent1 = Intent(view.context, SubmitWorkActivity::class.java)
            intent1.putExtra("submissionId", submissionId)
            view.context.startActivity(intent1)
        })

        // Deadline Date
        val dlDate = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(holder.sub_date.text as String)
        val dlHourFormat = SimpleDateFormat("HH")
        val dlMinuteFormat = SimpleDateFormat("mm")

        val dlHour = Integer. parseInt(dlHourFormat.format(dlDate as Date))
        val dlMinute = Integer. parseInt(dlMinuteFormat.format(dlDate))

        val calendar1 = Calendar.getInstance()
        calendar1.set(Calendar.HOUR_OF_DAY, dlHour)
        calendar1.set(Calendar.MINUTE, dlMinute)


        // Current Date
        val currentDate = Calendar.getInstance().time

        val calendar2 = Calendar.getInstance()
        val currentTime = Date()
        calendar2.setTime(currentTime)

        fun checkDate(){
            //         Check if the current date is after the deadline
            if(currentDate.after(dlDate)){
                holder.Status.setBackgroundResource(R.color.deep_red)
                holder.cardView.setBackgroundResource(R.color.red);
                holder.Status.setText("Overdue")
            }
            else if(currentDate == dlDate && calendar2.after(calendar1)){
                holder.Status.setBackgroundResource(R.color.deep_red)
                holder.cardView.setBackgroundResource(R.color.red);
                holder.Status.setText("Overdue")
            }
        }

            // Check status
            when (holder.Status.text){
                "" -> {
                    holder.Status.setBackgroundColor(Color.TRANSPARENT)
                    //holder.Status.setBackgroundColor(Color.parseColor("#e7eecc"))
                    holder.cardView.setBackgroundResource(R.color.grey);
                    holder.Status.setText("")
                    checkDate()

                }
                "Pending" -> {
                    holder.Status.setBackgroundResource(R.color.deep_yellow)
                    holder.cardView.setBackgroundResource(R.color.yellow);
                }
                else -> {
                    checkDate()
                }
            }
    }

    override fun getItemCount(): Int {
        return submissionList.size
    }
}