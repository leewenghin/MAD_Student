package com.example.project.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.project.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class OngoingAdapter(private val submissionList: ArrayList<Submission>) :
    RecyclerView.Adapter<OngoingAdapter.MyViewHolder>() {

    private var intent1: Intent? = null
    private var intent2: Intent? = null
    private var intent3: Intent? = null
    private var intent4: Intent? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val label: TextView = itemView.findViewById(R.id.icon_title)
        val due_date: TextView = itemView.findViewById(R.id.subDate)
        val Status: TextView = itemView.findViewById(R.id.status)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val Title: TextView = itemView.findViewById(R.id.project_title)
        val colorStateListYellow = ContextCompat.getColorStateList(itemView.context,
            R.color.deep_yellow
        )
        val colorStateListRed = ContextCompat.getColorStateList(itemView.context, R.color.deep_red)
        val colorStateListGreen = ContextCompat.getColorStateList(itemView.context,
            R.color.deep_green
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.label.text = submissionList[position].label
        holder.due_date.text = submissionList[position].deadline
        holder.Status.text = submissionList[position].submission_status
        holder.Title.text = submissionList[position].title

        // Replacing the submission_status

        val submissionId = submissionList[position].submission_id
        val submissionStatus = submissionList[position].submission_status
        val abstract = submissionList[position].abstract
        val title = submissionList[position].title
        val deadline = submissionList[position].deadline

        // Deadline Date
        val dlDate = SimpleDateFormat("dd-MM-yyyy HH:mm").parse(deadline!!)
        val dlHourFormat = SimpleDateFormat("HH")
        val dlMinuteFormat = SimpleDateFormat("mm")

        val dlHour = Integer.parseInt(dlHourFormat.format(dlDate as Date))
        val dlMinute = Integer.parseInt(dlMinuteFormat.format(dlDate))

        val calendar1 = Calendar.getInstance()
        calendar1.set(Calendar.HOUR_OF_DAY, dlHour)
        calendar1.set(Calendar.MINUTE, dlMinute)

        // Current Date
        val currentDate = Calendar.getInstance().time

        val calendar2 = Calendar.getInstance()
        val currentTime = Date()
        calendar2.setTime(currentTime)

        var overdue = false

        fun checkDate() {
            //Check if the current date is after the deadline
            if (currentDate.after(dlDate)) {
                holder.Status.visibility = View.VISIBLE
                holder.Status.backgroundTintList = holder.colorStateListRed
                // holder.Status.setBackgroundResource(R.color.deep_red)
                holder.cardView.setBackgroundResource(R.color.red);
                holder.Status.text = "Overdue"
                overdue = true

            } else if (currentDate == dlDate && calendar2.after(calendar1)) {
                holder.Status.visibility = View.VISIBLE
                holder.Status.backgroundTintList = holder.colorStateListRed
                // holder.Status.setBackgroundResource(R.color.deep_red)
                holder.cardView.setBackgroundResource(R.color.red);
                holder.Status.text = "Overdue"
                overdue = true
            } else{
                overdue = false
            }
        }

        // Replace student mark
//        val db = FirebaseFirestore.getInstance()
//        val userId = FirebaseAuth.getInstance().currentUser!!.uid
//
//        db.collection("submission").document(submissionId.toString()).get()
//            .addOnSuccessListener { submissionSnapshot ->
//                submissionSnapshot.reference.collection("users").document(userId).get()
//                    .addOnSuccessListener { usersSnapshot ->
//                        usersSnapshot.reference.collection("mark").get()
//                            .addOnSuccessListener { markSnapshot ->
//                                for(data in markSnapshot){
//                                    val mark = data.getString("proposal")
//                                    holder.label.text = mark
//                                }
//                            }
//                    }
//            }

        holder.cardView.setOnClickListener { view ->
            Toast.makeText(view.context, submissionId, Toast.LENGTH_LONG)
                .show()

            // If new, overdue, pending(title), then show submit activity
            fun titleSubmit(){
                intent1 = Intent(view.context, TitleSubmissionActivity::class.java)
                intent1!!.putExtra("submissionId", submissionId)
                intent1!!.putExtra("label", holder.label.text)
                intent1!!.putExtra("deadline", holder.due_date.text)
                intent1!!.putExtra("overdue", overdue)

                intent1!!.putExtra("submissionStatus", submissionStatus)
                intent1!!.putExtra("title", title)
                intent1!!.putExtra("abstract", abstract)
                view.context.startActivity(intent1)
            }

            // If Pending, Rejected or Approve, then show detail.
            fun titleDetail(){
                val intent1 = Intent(view.context, TitleSubmissionDetailActivity::class.java)
                intent1.putExtra("submissionId", submissionId)
                view.context.startActivity(intent1)
            }

            // If new, overdue, pending(title), then show submit activity
            fun posterSubmit(){
                intent2 = Intent(view.context, PosterSubmissionActivity::class.java)
                intent2!!.putExtra("submissionId", submissionId)
                intent2!!.putExtra("label", holder.label.text)
                intent2!!.putExtra("deadline", holder.due_date.text)
                intent2!!.putExtra("overdue", overdue)

                intent2!!.putExtra("submissionStatus", submissionStatus)
                intent2!!.putExtra("title", title)
                intent2!!.putExtra("abstract", abstract)
                view.context.startActivity(intent2)
            }

            // If Pending, Rejected or Approve, then show detail.
            fun posterDetail(){
                val intent1 = Intent(view.context, PosterSubmissionDetailActivity::class.java)
                intent1.putExtra("submissionId", submissionId)
                view.context.startActivity(intent1)
            }

            // If Pending, Rejected or Approve, then show detail.
            fun proposalSubmit(){
                val intent3 = Intent(view.context, ProposalSubmissionActivity::class.java)
                intent3.putExtra("submissionId", submissionId)
                intent3.putExtra("label", holder.label.text)
                intent3.putExtra("deadline", holder.due_date.text)
                intent3.putExtra("overdue", overdue)

                intent3.putExtra("submissionStatus", submissionStatus)
                intent3.putExtra("title", title)
                intent3.putExtra("abstract", abstract)
                view.context.startActivity(intent3)
            }

            // If new, overdue, pending(title), then show submit activity
            fun proposalDetail(){
                intent3 = Intent(view.context, ProposalSubmissionDetailActivity::class.java)
                intent3!!.putExtra("submissionId", submissionId)
                view.context.startActivity(intent3)
            }

            // If Pending, Rejected or Approve, then show detail.
            fun thesisSubmit(){
                val intent4 = Intent(view.context, ThesisSubmissionActivity::class.java)
                intent4.putExtra("submissionId", submissionId)
                intent4.putExtra("label", holder.label.text)
                intent4.putExtra("deadline", holder.due_date.text)
                intent4.putExtra("overdue", overdue)

                intent4.putExtra("submissionStatus", submissionStatus)
                intent4.putExtra("title", title)
                intent4.putExtra("abstract", abstract)
                view.context.startActivity(intent4)
            }

            // If new, overdue, pending(title), then show submit activity
            fun thesisDetail(){
                intent4 = Intent(view.context, ThesisSubmissionDetailActivity::class.java)
                intent4!!.putExtra("submissionId", submissionId)
                view.context.startActivity(intent4)
            }

            when(holder.label.text){
                "Title" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            titleSubmit()
                        }
                        "Overdue" -> {
                            titleSubmit()
                        }
                        "Pending" -> {
                            titleSubmit()
                        }
                        "Rejected" -> {
                            titleDetail()
                        }
                        "Approved" -> {
                            titleDetail()
                        }
                    }
                }
                "Poster" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            posterSubmit()
                        }
                        "Overdue" -> {
                            posterSubmit()
                        }
                        "Pending" -> {
                            posterSubmit()
                        }
                        "Rejected" -> {
                            posterDetail()
                        }
                        "Approved" -> {
                            posterDetail()
                        }
                    }
                }
                "Proposal PPT" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            proposalSubmit()
                        }
                        "Overdue" -> {
                            proposalSubmit()
                        }
                        "Pending" -> {
                            proposalSubmit()
                        }
                        "Rejected" -> {
                            proposalDetail()
                        }
                        "Approved" -> {
                            proposalDetail()
                        }
                    }
                }
                "Proposal Report" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            proposalSubmit()
                        }
                        "Overdue" -> {
                            proposalSubmit()
                        }
                        "Pending" -> {
                            proposalSubmit()
                        }
                        "Rejected" -> {
                            proposalDetail()
                        }
                        "Approved" -> {
                            proposalDetail()
                        }
                    }
                }
                "Thesis PPT" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            thesisSubmit()
                        }
                        "Overdue" -> {
                            thesisSubmit()
                        }
                        "Pending" -> {
                            thesisSubmit()
                        }
                        "Rejected" -> {
                            thesisDetail()
                        }
                        "Approved" -> {
                            thesisDetail()
                        }
                    }
                }
                "Thesis Report" -> {
                    // Overdue, Pending, Rejected, Approve / Graded
                    when (holder.Status.text) {
                        "" -> {
                            thesisSubmit()
                        }
                        "Overdue" -> {
                            thesisSubmit()
                        }
                        "Pending" -> {
                            thesisSubmit()
                        }
                        "Rejected" -> {
                            thesisDetail()
                        }
                        "Approved" -> {
                            thesisDetail()
                        }
                    }
                }
            }
        }

        // If project_title is blank then Textview will gone
        if(holder.Title.text == "" ){
            holder.Title.visibility = View.GONE
        }

        // Check status
        when (holder.Status.text) {
            "" -> {
                holder.Status.visibility = View.INVISIBLE
                holder.cardView.setBackgroundResource(R.color.grey);
                // holder.Status.setBackgroundColor(Color.TRANSPARENT)
                // holder.Status.setBackgroundColor(Color.parseColor("#e7eecc"))
                checkDate()
            }
            "Pending" -> {
                holder.Status.backgroundTintList = holder.colorStateListYellow
                // holder.Status.setBackgroundResource(R.color.deep_yellow)
                holder.cardView.setBackgroundResource(R.color.yellow);
            }
            "Approved" -> {
                holder.Status.backgroundTintList = holder.colorStateListGreen
                holder.cardView.setBackgroundResource(R.color.green);
            }
            else -> {}
        }
    }

    override fun getItemCount(): Int {
        return submissionList.size
    }
}