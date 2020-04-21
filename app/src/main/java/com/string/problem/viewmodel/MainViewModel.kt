package com.string.problem.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var mutableOlderStackData = MutableLiveData<ArrayList<String>>()
    var mutableRecentStackData = MutableLiveData<ArrayList<String>>()

    fun addStringValueToQueue(stringValue: String) {

    }

    fun addList() {
        val list = ArrayList<String>()
        list.add("Abcde")
        list.add("Abcde")
        list.add("Abcde")
        list.add("Abcde")
        list.add("Abcde")

        mutableOlderStackData.value = list
        mutableRecentStackData.value = list
    }
}