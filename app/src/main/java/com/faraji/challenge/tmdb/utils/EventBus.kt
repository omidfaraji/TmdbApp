package com.faraji.challenge.tmdb.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class EventBus {
    private val channel = BroadcastChannel<Any>(1)

    fun send(event: Any, context: CoroutineContext = Dispatchers.Default) {
        CoroutineScope(context).launch {
            channel.send(event)
        }
    }

    fun subscribe(): Flow<Any> = channel.asFlow()

    inline fun <reified T> subscribeToEvent() =
        subscribe().filter { it is T }.map { it as T }


    inline fun <reified T> collectEventOnMainThread(crossinline onNext: (T) -> Unit) =
        CoroutineScope(Dispatchers.Main).launch {
            subscribeToEvent<T>().collect { onNext(it) }
        }

    companion object {
        val instance = EventBus()
    }
}