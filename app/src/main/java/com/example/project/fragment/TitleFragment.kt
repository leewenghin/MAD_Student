package com.example.project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.TitleAdapter
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

                val submissionReference = db.collection("submission")
                    .whereEqualTo("label", "Title").whereEqualTo("batch",studentBatch)

                val submissionReference2 = db.collection("submission")

                        submissionReference.get().addOnSuccessListener { submissionSnapshot ->

                            // create a list to store the data of the subcollection
                            val dataList = mutableListOf<Map<String, Any>>()
                            dataList.clear()

                             // store the data of the first document in a variable
//                            val parentData = submissionSnapshot.first().data
//                            Toast.makeText(context, dataList.joinToString(""), Toast.LENGTH_LONG).show()
                            val i = 1

                            for (document in submissionSnapshot) {
                            // retrieve the data of the current document
                            val currentDocumentId = document.id

                                document.reference.collection("users").get()
                                    .addOnSuccessListener { subcollectionSnapshot ->
                                        if (!subcollectionSnapshot.isEmpty) {
                                            for (data in subcollectionSnapshot) {
                                                // store the loop data for subcollection in a variable
                                                val parentData = document.data

                                                // Toast.makeText(context, parentData.toString(), Toast.LENGTH_LONG).show()

                                                // store the parentData data for subcollection in the list
                                                dataList.add(parentData)

                                                // Process the subdocument
                                                val submission: Submission? =
                                                    data.toObject(Submission::class.java)
                                                if (submission != null) {
                                                    submissionList.add(submission)
                                                }
                                            }
                                            recyclerView.adapter = TitleAdapter(submissionList)
                                        }
                                        else {
                                            val testing = i + 1

                                            submissionReference.get()
                                                .addOnSuccessListener { submissionSnapshot2 ->

                                                    for (data in submissionSnapshot2) {
                                                        Toast.makeText(context, testing.toString(), Toast.LENGTH_LONG).show()
                                                        // retrieve the data of the current document
                                                        val currentData = data.data

                                                        // Toast.makeText(context, currentData.toString(), Toast.LENGTH_LONG).show()

                                                        // check if the data of the current document is the same
                                                        // as the stored data list
                                                        if(dataList.contains(currentData)){
                                                            // the submission is the same, skip the loop
                                                            continue

                                                        }else{
                                                            // Process the subdocument
                                                            val submission: Submission? =
                                                                data.toObject(Submission::class.java)
                                                            if (submission != null) {
                                                                submissionList.add(submission)

                                                            }
                                                        }
                                                    }
                                                    recyclerView.adapter = TitleAdapter(submissionList)
                                                }
                                        }
                                    }
                            }
                        }

//                db.collection("submission")
//                    .whereEqualTo("label", "Title").whereEqualTo("batch",studentBatch).get()
//                    .addOnSuccessListener { testing ->
//                        for (document in testing) {
//                            document.reference.collection("users").get()
//                                .addOnSuccessListener { subcollectionSnapshot ->
//                                    if(!subcollectionSnapshot.isEmpty){
//                                    for (data in subcollectionSnapshot) {
//                                        // Process the subdocument
//                                        val submission: Submission? =
//                                            data.toObject(Submission::class.java)
//                                        if (submission != null) {
//                                            submissionList.add(submission)
//                                        }
//                                    }
//                                    recyclerView.adapter = TitleAdapter(submissionList)
//                                    }
//                                }
//                        }
//                    }
//                                .addOnFailureListener { exception ->
//                                    // Handle the error
//                                }
//                        }
//                    }

//                db.collection("submission")
//                    .whereEqualTo("label", "Title").whereEqualTo("batch",studentBatch)
//                    .get()
//                    .addOnSuccessListener { submissionSnapshot ->
//                        if(!submissionSnapshot.isEmpty){
//
//                            for(data in submissionSnapshot.documents){
//                                val submission: Submission? = data.toObject(Submission::class.java)
//                                if(submission != null){
//                                    submissionList.add(submission)
//                                }
//                            }
//                            recyclerView.adapter = TitleAdapter(submissionList)
//                        }
//                    }
//                    .addOnFailureListener{
//                        Toast.makeText(context, it.toString(), Toast.LENGTH_LONG).show()
//                    }
            }
        }

        return view
    }

}