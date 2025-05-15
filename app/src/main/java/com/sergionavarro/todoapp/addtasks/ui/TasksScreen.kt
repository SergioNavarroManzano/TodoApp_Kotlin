package com.sergionavarro.todoapp.addtasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sergionavarro.todoapp.addtasks.ui.model.TaskModel

@Composable
fun TasksScreen(modifier: Modifier, tasksViewModel: TasksViewModel) {

    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)

    Box(modifier = modifier.fillMaxSize()) {

        AddTasksDialog(
            showDialog,
            onDismiss = { tasksViewModel.onDialongClose() },
            onTaskAdded = { tasksViewModel.onTasksAdded(it) })

        FabDialog(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            tasksViewModel
        )

        TasksList(tasksViewModel)

    }
}

@Composable
fun TasksList(tasksViewModel: TasksViewModel) {

    val myTasks:List<TaskModel> = tasksViewModel.task
    LazyColumn() {
        items(myTasks, key={it.id}){ task->//dandole la key optima el lazy column
            ItemTask(task,tasksViewModel)
        }
    }
}

@Composable
fun ItemTask(taskModel:TaskModel,tasksViewModel: TasksViewModel) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).pointerInput(Unit){
            detectTapGestures(onLongPress = {
                tasksViewModel.onItemRemove(taskModel)
            })
        },
        elevation = CardDefaults.cardElevation(8.dp),

    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("${taskModel.task}", modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
            Checkbox(checked = taskModel.selected, onCheckedChange = {tasksViewModel.onCheckBoxSelected(taskModel)})
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(onClick = {
        //TODO MOSTRAR DIALOGO
        tasksViewModel.onShowDialogClick()
    }, modifier = modifier) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTasksDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {

    var myTask by rememberSaveable { mutableStateOf("") }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    "Añade tu tarea",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),

                    )
                Spacer(Modifier.size(26.dp))
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    singleLine = true,
                    maxLines = 1,
                    modifier = Modifier.background(Color.White)
                )
                Spacer(Modifier.size(26.dp))

                Button(onClick = {
                    //TODO MANDAR TAREA
                    onTaskAdded(myTask)
                    myTask=""
                }, Modifier.fillMaxWidth()) {
                    Text("Añadir tarea")
                }
            }
        }
    }
}
