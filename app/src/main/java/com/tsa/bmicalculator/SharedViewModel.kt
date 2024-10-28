package com.tsa.bmicalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SharedViewModel : ViewModel() {
    private val _bmi = MutableLiveData<Double>()
    val bmi: LiveData<Double> get() = _bmi

    private val _weightNeeded = MutableLiveData<Double>()
    val weightNeeded: LiveData<Double> get() = _weightNeeded

    private val _lastCheckDate = MutableLiveData<String>()
    val lastCheckDate: LiveData<String> get() = _lastCheckDate

    fun setBmi(value1: Double,value2:Double) {
        _bmi.value = value1
        _weightNeeded.value=value2
        _lastCheckDate.value = getCurrentDate()
    }
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}