package com.example.todolist.data

data class Task(val id:Int, var label:String, var detail: String = "", var priority:Int= 0 )