package com.faraji.challenge.tmdb.utils

class NetworkErrorEvent(
    val throwable: Throwable,
    val onRetry: (() -> Unit)? = null,
    val onCancel: (() -> Unit)? = null
)


