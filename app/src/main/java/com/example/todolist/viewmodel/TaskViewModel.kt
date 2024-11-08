package com.example.todolist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.todolist.data.Task

class TaskViewModel : ViewModel() {
    private  var _listOfTasks = mutableStateListOf<Task>()
    var showBottomSheet by mutableStateOf(false)
    var editMode by mutableStateOf(false)

    val listOfTasks:List<Task>
        get() = _listOfTasks.toList()

    fun addItem(task: Task){
        _listOfTasks.add(task)
    }

    fun deleteItem(index: Int){
        _listOfTasks.removeAt(index)
    }

    fun editItem(index:Int, task: Task){
        _listOfTasks[index] = task
    }

    override fun onCleared() {
        super.onCleared()

    }
}