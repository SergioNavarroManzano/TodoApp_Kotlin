package com.sergionavarro.todoapp.addtasks.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor() :ViewModel() {


    private val _showDialog=MutableLiveData<Boolean>()

    val showDialog:LiveData<Boolean> = _showDialog


    fun onDialongClose() {
        _showDialog.value=false
    }

    fun onTasksAdded(task: String) {
        Log.i("Navarro",task)
        _showDialog.value=false
    }

    fun onShowDialogClick(){
        _showDialog.value=true
    }
}