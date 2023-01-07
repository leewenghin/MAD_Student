package com.example.project.fragment

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.MyAdapter
import com.example.project.R
import com.example.project.Submission
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


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

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document(userId).get().addOnSuccessListener { userSnapshot ->
            if(userSnapshot.exists()){
                val userData = userSnapshot.data
                val studentBatch = userData?.get("batch") as String

                db.collection("submission")
                    .whereEqualTo("label", "Title").whereEqualTo("batch",studentBatch)
                    .get()
                    .addOnSuccessListener { submissionSnapshot ->
                        if(!submissionSnapshot.isEmpty){
                            for(data in submissionSnapshot.documents){
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
            }
        }

        return view
    }

}