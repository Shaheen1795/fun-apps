package com.example.todolist.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.data.Task
import com.example.todolist.viewmodel.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterTaskDetailScreen(onDismiss: () -> Unit = {}, taskViewModel: TaskViewModel, sheetState: SheetState, coroutineScope: CoroutineScope, index:Int = 0 ){


    var taskName by remember {   mutableStateOf("")}

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState, modifier = Modifier.padding(10.dp)) {

        Column(modifier = Modifier.padding(20.dp)){

            Text("Enter your task", modifier = Modifier.padding(20.dp))
            TextField(taskName, onValueChange = {it ->
                taskName = it

            }, modifier = Modifier.padding(10.dp))

            Button(onClick = {
                coroutineScope.launch {
                    sheetState.hide()
                    if(taskViewModel.editMode){
                        taskViewModel.editItem(index,Task(0,taskName))
                        taskViewModel.editMode = false
                    }
                    else{
                        taskViewModel.addItem(Task(0,taskName))
                    }
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        taskViewModel.showBottomSheet = false
                    }
                }
            }, modifier = Modifier.padding(10.dp)) {

                Text("Confirm")
            }

        }
    }
}