package com.sergionavarro.todoapp.addtasks.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergionavarro.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() :ViewModel() {


    private val _showDialog=MutableLiveData<Boolean>()
    val showDialog:LiveData<Boolean> = _showDialog

    private val _tasks= mutableStateListOf<TaskModel>()
    val task:List<TaskModel> = _tasks


    fun onDialongClose() {
        _showDialog.value=false
    }

    fun onTasksAdded(task: String) {
        _tasks.add(TaskModel(task=task))
        _showDialog.value=false
    }

    fun onShowDialogClick(){
        _showDialog.value=true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        val index=_tasks.indexOf(taskModel)
        //asi se furza la recomposicion de la view
        _tasks[index]=_tasks[index].let {
            it.copy(selected = !it.selected)
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        val task=_tasks.find { it.id==taskModel.id }
        _tasks.remove(task)
    }
}