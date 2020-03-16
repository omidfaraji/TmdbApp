package com.faraji.challenge.tmdb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.faraji.challenge.tmdb.R
import com.faraji.challenge.tmdb.extentions.networkErrorDialog
import com.faraji.challenge.tmdb.utils.EventBus
import com.faraji.challenge.tmdb.utils.NetworkErrorEvent
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val eventBus: EventBus by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvents()
    }

    private fun initEvents() {
        eventBus.collectEventOnMainThread<NetworkErrorEvent> {
            networkErrorDialog(
                it.throwable.message ?: getString(R.string.network_problem),
                it.onRetry,
                it.onCancel
            )
        }
    }


}
