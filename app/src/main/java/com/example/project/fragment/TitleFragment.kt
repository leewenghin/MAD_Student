package com.example.project.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.ItemObject
import com.example.project.MyAdapter
import com.example.project.R
import com.example.project.Submission
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class TitleFragment: Fragment()  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var submissionList: ArrayList<Submission>
    private var db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_title_work, container, false)
        val itemView = inflater.inflate(R.layout.item_layout, container, false)
        recyclerView = view.findViewById(R.id.recycleview_result)
        recyclerView.layoutManager = LinearLayoutManager(context)

        submissionList = arrayListOf()

        db.collection("submission").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val submission: Submission? = data.toObject(Submission::class.java)
                        if(submission != null){
                            submissionList.add(submission)
                        }
                    }
                    recyclerView.adapter = MyAdapter(submissionList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
            }

//        db.collection("submission")
//            .whereEqualTo("status", "New")
//            .get()
//            .addOnSuccessListener {
//                for (data in it.documents) {
//                    // Set the background of the TextView to transparent
//                    textView.setBackgroundColor(Color.TRANSPARENT)
//                }
//            }

        return view
    }

}