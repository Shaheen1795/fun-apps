package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.screens.EnterTaskDetailScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                 ListOfTasksView(TaskViewModel())

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfTasksView(taskViewModel: TaskViewModel){
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    if(taskViewModel.showBottomSheet){
        EnterTaskDetailScreen({ taskViewModel.showBottomSheet = false },taskViewModel,sheetState,coroutineScope)
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        if(taskViewModel.listOfTasks.isEmpty()){
            Text("Let's plan something !")
        }
    }
    Column(modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp)) {
        Button(onClick = {
            taskViewModel.showBottomSheet= true
        }, modifier = Modifier.padding(20.dp)) {
            Text("Add task")
        }

        LazyColumn(userScrollEnabled = true,modifier =Modifier.wrapContentHeight()) {
            itemsIndexed(taskViewModel.listOfTasks){ index, item ->  ListItem(Modifier.fillMaxWidth().padding(10.dp).background(Color.LightGray).clickable {
                taskViewModel.showBottomSheet = true
                taskViewModel.editMode = true
                }.animateItem(),index,item.label,taskViewModel)
            }
        }
    }

}

@Composable
fun ListItem(modifier: Modifier,index:Int = 0, label:String = "Example Text", taskViewModel: TaskViewModel){
    var checkedText by remember { mutableStateOf(false) }
    Row(modifier = modifier) {

        Checkbox(checked = false, onCheckedChange = {
            checkedText = !checkedText
        }, modifier = Modifier.padding(10.dp))
        Text(text = label, modifier = Modifier.padding(25.dp).weight(75f), textDecoration = if(checkedText) TextDecoration.LineThrough else TextDecoration.None)

        Button({ taskViewModel.deleteItem(index) }, modifier = Modifier.padding(25.dp)) {
            Text("Delete")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListTheme {
        Greeting("Android")
    }
}