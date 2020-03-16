package com.faraji.challenge.tmdb.base

import androidx.lifecycle.ViewModel
import com.faraji.challenge.tmdb.extentions.clearAllDisposables
import kotlinx.coroutines.Job


abstract class BaseViewModel : ViewModel() {

    private val disposableJobs by lazy { mutableListOf<Job>() }

    fun addToUnsubscribe(job: Job) {
        disposableJobs.add(job)
    }

    override fun onCleared() {
        disposableJobs.clearAllDisposables()
        super.onCleared()
    }


}