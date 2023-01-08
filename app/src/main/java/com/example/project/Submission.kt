package com.example.project

import io.grpc.Deadline

data class Submission(
    val label:String?=null,
    val deadline:String?=null,
    val status:String?=null,
    val id:String?=null
)
