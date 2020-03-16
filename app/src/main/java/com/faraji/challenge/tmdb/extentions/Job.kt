package com.faraji.challenge.tmdb.extentions

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren

fun Job.cancelTree() {
    cancelChildren()
    cancel()
}

fun List<Job>.clearAllDisposables() {
    forEach {
        it.cancelTree()
    }
}