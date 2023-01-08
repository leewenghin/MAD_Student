package com.example.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SubmitWorkActivity: AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_submit_work)
        val submissionId = intent.getStringExtra("submissionId")
        Toast.makeText(this, submissionId, Toast.LENGTH_LONG).show()


        val title = findViewById<EditText>(R.id.input_title)
        val comment = findViewById<EditText>(R.id.input_comment)
        val btn_submit = findViewById<Button>(R.id.submit_button)

        btn_submit.setOnClickListener {
            val Title = title.text.toString().trim()
            val Comment = comment.text.toString().trim()

            // Current Date Time format
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

            // Current Date Time
            val currentDateTime = Calendar.getInstance().time

            // Convert current date time from (Date to String)
            val dateSubmit = dateFormat.format(currentDateTime)

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            // Get to same submitted submission id of "submission" collection
            val newDocument = db.collection("submission").document(submissionId.toString())

            val data = mapOf(
                "id" to newDocument.id,
                "title" to Title,
                "abstract" to Comment,
                "submission_date" to dateSubmit,
                "status" to "Pending"
            )

            newDocument.collection("users").document(userId).set(data)
                .addOnSuccessListener { documentReference ->
                // The data was successfully saved
                Toast.makeText(baseContext, "Submission has been submitted successfully",
                    Toast.LENGTH_LONG).show()
            }
                .addOnFailureListener{
                    // The save failed
                    Toast.makeText(baseContext, "Submission was not able to submit, please try again",
                        Toast.LENGTH_LONG).show()
                }
        }
    }
}